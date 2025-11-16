import { Navigate } from "react-router-dom";
import LoadingScreen from "../components/LoadingScreen";
import { useAuth } from "../context/AuthContext";

const AuthRedirect = () => {

    const {isAuthenticated, isLoading} = useAuth();

    if (isLoading) return <LoadingScreen />;

    return isAuthenticated ?
        <Navigate to="/dashboard" replace /> : <Navigate to="/login" replace />;

}

export default AuthRedirect;