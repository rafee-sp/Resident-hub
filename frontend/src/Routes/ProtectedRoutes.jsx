import { Navigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import LoadingScreen from "../components/LoadingScreen";

const ProtectedRoute = ({ children }) => {
  
    const { isAuthenticated, isLoading } = useAuth();
  
    if (isLoading) return <LoadingScreen />;

  if (!isAuthenticated) return <Navigate to="/login" replace />;
  
  return children;
};

export default ProtectedRoute;
