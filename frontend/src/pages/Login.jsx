import { useState } from "react";
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";
import { EyeIcon, EyeSlashIcon } from "@heroicons/react/24/outline";

const loginDefault = {
  userName: "",
  password: "",
};

const errorObj = {
  userName: "",
  password: "",
  login: "",
};

export default function Login() {
  const [loginData, setLoginData] = useState(loginDefault);
  const [errors, setErrors] = useState(errorObj);
  const [loading, setLoading] = useState(false);
  const [showPassword, setShowPassword] = useState(false);

  const { login } = useAuth();
  const navigate = useNavigate();

  const handleLogin = async () => {
    if (!validate()) return;

    setErrors(errorObj);
    setLoading(true);

    try {
      await login(loginData.userName, loginData.password);
      navigate("/dashboard");
    } catch {
      setErrors((prev) => ({
        ...prev,
        login: "Invalid Credentials",
      }));
    } finally {
      setLoading(false);
    }
  };

  const validate = () => {
    let newErrors = { ...errorObj };

    if (!/^[A-Za-z]+$/.test(loginData.userName.trim())) {
      newErrors.userName = "Enter valid username";
    }

    const passwordRegex =
      /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/;

    if (!loginData.password || !passwordRegex.test(loginData.password.trim())) {
      newErrors.password =
        "Password must be 8-20 chars, include uppercase, lowercase, number & special char";
    }

    setErrors(newErrors);

    return !newErrors.userName && !newErrors.password;
  };

  const handleChange = (e) => {
    const { name, value } = e.target;

    if (name === "userName" && !/^[A-Za-z]*$/.test(value)) return;

    setLoginData((prev) => ({ ...prev, [name]: value }));
    setErrors((prev) => ({ ...prev, [name]: "", login: "" }));
  };

  return (
    <div className="h-full flex items-center justify-center bg-gradient-to-br from-red-700 via-red-900 to-black relative overflow-hidden">
      {/* Layered pulse circles 
      <div className="absolute w-[300%] h-[300%] top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 bg-red-600 rounded-full opacity-20 animate-ping-slow"></div>
      <div className="absolute w-[200%] h-[200%] top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 bg-red-500 rounded-full opacity-15 animate-ping-slower"></div>

      */}

      {/* Login Card */}
      <div className="z-10 bg-white/90 backdrop-blur-lg rounded-3xl shadow-2xl w-96 p-8 flex flex-col items-center border-2 border-red-600">
        <div className="flex items-center mb-6">
          <h1 className="text-2xl font-bold text-red-700 uppercase tracking-wider">
            Login
          </h1>
        </div>

        <div className="w-full flex flex-col gap-4">
          <div>
            <input
              name="userName"
              className="w-full border border-red-400 rounded-lg p-3 focus:outline-none focus:ring-2 focus:ring-red-600 transition placeholder-gray-500"
              placeholder="Username"
              value={loginData.userName}
              onChange={(e) => handleChange(e)}
              maxLength={20}
            />
            {errors.userName && (
              <p className="text-red-600 mb-1 text-left font-medium">
                {errors.userName}
              </p>
            )}
          </div>

          <div className="relative">
            <input
              name="password"
              type={showPassword ? "text" : "password"}
              className="w-full border border-red-400 rounded-lg p-3 focus:outline-none focus:ring-2 focus:ring-red-600 transition placeholder-gray-500"
              placeholder="Password"
              value={loginData.password}
              onChange={(e) => handleChange(e)}
              onKeyDown={(e) => e.key === "Enter" && handleLogin()}
              maxLength={20}
            />
            <button
              type="button"
              className="absolute right-3 top-1/2 -translate-y-1/2 text-gray-500 hover:text-gray-500"
              onClick={() => setShowPassword((prev) => !prev)}
            >
              {showPassword ? (
                <EyeSlashIcon className="w-5 h-5" />
              ) : (
                <EyeIcon className="w-5 h-5" />
              )}
            </button>
          </div>
          {errors.password && (
            <p className="text-red-600 mb-1 text-left font-medium">
              {errors.password}
            </p>
          )}
          {errors.login && (
            <p className="text-red-600 mb-4 text-center font-medium">
              {errors.login}
            </p>
          )}
          <div>
            <button
              disabled={loading}
              className="w-full bg-gradient-to-r from-red-600 to-red-700 text-white font-semibold rounded-lg p-3 hover:from-red-700 hover:to-red-800 transition disabled:opacity-60"
              onClick={handleLogin}
            >
              {loading ? "Signing in..." : "Sign In"}
            </button>
          </div>
        </div>

        <p className="mt-6 text-gray-700 text-sm text-center">
          Authorized personnel only. All actions are monitored.
        </p>
      </div>

      <div className="absolute inset-0 bg-black opacity-10 pointer-events-none animate-pulse-slow"></div>
    </div>
  );
}
