import axios from "axios";
import { tokenStore } from "../store/tokenStore";
import authApi from "./authApi";
import {toast} from "react-toastify";
import { generateRequestId } from "../utils/generateRequestId";

const api = axios.create({
  baseURL: import.meta.env.VITE_BACKEND_URL,
  withCredentials: true,
  timeout: 10000,
});

api.interceptors.request.use((config) => {
  const accessToken = tokenStore.get();

  config.headers["X-REQUEST-ID"] = generateRequestId();

  if (accessToken) config.headers["Authorization"] = `Bearer ${accessToken}`;
  return config;
});

let isRefreshing = false;
let pendingQueue = [];

const processQueue = (error, token = null) => {
  pendingQueue.forEach(({ resolve, reject }) => {
    if (token) {
      resolve(token);
    } else {
      reject(error);
    }
  });

  pendingQueue = [];
};

api.interceptors.response.use(
  (res) => res,
  async (error) => {
    const original = error.config;
    if (error.response?.status === 401 && !original._retry) {
      if (isRefreshing) {
        return new Promise((resolve, reject) => {
          pendingQueue.push({ resolve, reject });
        }).then((token) => {
          original.headers["Authorization"] = `Bearer ${token}`;
          return api(original);
        });
      }
      original._retry = true;
      isRefreshing = true;

      try {
        const { data } = await authApi.post(
          "/refresh-token",
          {},
          {
            withCredentials: true,
          }
        );
        tokenStore.set(data.accessToken);
        processQueue(null, data.accessToken);
        return api(original);
      } catch (e) {
        processQueue(e, null);
        window.location.href = "/login";
        throw e;
      } finally {
        isRefreshing = false;
      }    
    }

    switch (error.response?.status) {
      case 400:
        toast.error("Bad request. Please check your input.");
        break;
      case 403:
        toast.error("Forbidden. You don't have permission.");
        break;
      case 404:
        toast.error("Target Resource not found.");
        break;
      case 500:
        toast.error("Server error. Please try again later.");
        break;
      default:
        toast.error(error.message || "Something went wrong.");
    }

    throw Promise.reject(error);
  }
);

export default api;
