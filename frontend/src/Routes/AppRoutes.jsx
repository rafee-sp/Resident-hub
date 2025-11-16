import { Route, Routes } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import ProtectedRoute from "./ProtectedRoutes";
import Login from "../pages/Login";
import LoadingScreen from "../components/LoadingScreen";
import NotFound from "../pages/NotFound";
import { lazy, Suspense } from "react";

const Dashboard = lazy(() => import("../pages/Dashboard"));
const AuthRedirect = lazy(() => import("./AuthRedirect"));

const AppRoutes = () => {

    const { isLoading } = useAuth(); 

  if (isLoading) {
      return <LoadingScreen />;
  }

  return (
  <Suspense fallback={<LoadingScreen />}>
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route
        path="/dashboard"
        element={
          <ProtectedRoute>
            <Dashboard />
          </ProtectedRoute>
        }
      />
      <Route path="/" element={<AuthRedirect />} />
      <Route path="*" element={<NotFound />} />
    </Routes>
    </Suspense>
  );

}

export default AppRoutes;