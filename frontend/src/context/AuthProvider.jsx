import { useEffect, useMemo, useState } from "react";
import { tokenStore } from "../store/tokenStore";
import authApi from "../api/authApi";
import { AuthContext } from "./AuthContext";


export const AuthProvider = ({ children }) => {
  const [accessToken, setAccessToken] = useState(null);
  const [user, setUser] = useState(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const authInit = async () => {
      try {
        setIsLoading(true);
        await refreshToken();        
      } catch {
        tokenStore.clear();
        setAccessToken(null);
        setUser(null);
        setIsLoading(false);
      } finally {
        setIsLoading(false);
      }
    };
    authInit();
  }, []);

  const login = async (userName, password) => {
    const { data } = await authApi.post(
      "/login",
      { userName, password },
      { withCredentials: true }
    );

    tokenStore.set(data.accessToken);
    setAccessToken(data.accessToken);
    setUser(data.user);
    return data.user;
  };

  const logout = async () => {
    await authApi.post("/logout", {}, { withCredentials: true });
    tokenStore.clear();
    setAccessToken(null);
    setUser(null);
    
    return true;
  };

  const refreshToken = async () => {
    try {
      const { data } = await authApi.post(
        "/refresh-token",
        {},
        { withCredentials: true }
      );      
      tokenStore.set(data.accessToken);
      setAccessToken(data.accessToken);
      setUser(data.user);
    } catch {
      tokenStore.clear();
      setAccessToken(null);
      setUser(null);
    }
  };

  const value = useMemo(
    () => ({
      accessToken,
      user,
      isAuthenticated: !!accessToken,
      login,
      logout,
      isLoading,
    }),
    [accessToken, user, isLoading]
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};


