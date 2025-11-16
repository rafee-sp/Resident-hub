import axios from "axios";

const authApi = axios.create({
  baseURL: import.meta.env.VITE_BACKEND_AUTH_URL,
  withCredentials: true,
  timeout: 10000,
});

export default authApi;

