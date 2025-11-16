import { EyeIcon, EyeSlashIcon } from "@heroicons/react/24/outline";
import { useState } from "react";

const PasswordField = ({ label, name, value, onChange, error }) => {
  const [showPassword, setShowPassword] = useState(false);

  

  return (
    <div className="mb-4">
      <label className="block text-base mb-2 font-medium">
        {label}
        <span className="text-red-500 ms-0.5">*</span>
      </label>
      <div className="relative">
        <input
          name={name}
          type={showPassword ? "text" : "password"}
          className="w-full p-2 mb-1 text-sm border border-gray-300 outline-none focus:ring-2 focus:ring-indigo-600 rounded-lg text-blue-700 pr-10"
          value={value}
          onChange={onChange}
          maxLength={20}
        />
        <button
          type="button"
          className="absolute right-3 inset-y-0 flex items-center text-gray-500 hover:text-gray-500"
          onClick={() => setShowPassword((prev) => !prev)}
        >
          {showPassword ? (
            <EyeSlashIcon className="w-5 h-5" />
          ) : (
            <EyeIcon className="w-5 h-5" />
          )}
        </button>
      </div>
      {error && (
        <p className="text-red-600 mb-1 text-left font-medium">{error}</p>
      )}
    </div>
  );
};

export default PasswordField;
