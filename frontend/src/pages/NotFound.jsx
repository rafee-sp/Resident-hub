import { Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const NotFound = () => {
  const { isAuthenticated } = useAuth();

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100 text-center px-4">
      <h1 className="text-6xl font-bold text-blue-900 mb-4">404</h1>
      <h2 className="text-2xl font-semibold text-gray-800 mb-6">
        Oops! Page not found
      </h2>
      <p className="text-gray-600 mb-9">
        The page you're looking for doesn't exist or has been moved.
      </p>

      {isAuthenticated ? (
        <Link
          to="/dashboard"
          className="px-6 py-3 bg-blue-900 text-white rounded-lg shadow hover:bg-blue-800 transition"
        >
          Go to Dashboard
        </Link>
      ) : (
        <Link
          to="/login"
          className="px-6 py-3 bg-blue-900 text-white rounded-lg shadow hover:bg-blue-800 transition"
        >
          Back to Login
        </Link>
      )}
    </div>
  );
};

export default NotFound;
