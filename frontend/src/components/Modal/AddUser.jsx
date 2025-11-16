import { EyeIcon, EyeSlashIcon, XMarkIcon } from "@heroicons/react/24/outline";
import { useState } from "react";
import PasswordField from "../PasswordField";
import api from "../../api/api";

const initialFormData = {
  userName: "",
  email: "",
  password: "",
  rePassword: "",
};

const AddUser = ({ onClose, onSuccess }) => {
  const [userData, setUserData] = useState(initialFormData);
  const [errors, setErrors] = useState({});
  const [isLoading, setIsLoading] = useState(false);

  const validate = () => {
    let errors = {};

    if (!/^[A-Za-z]*$/.test(userData.userName.trim()))
      errors.userName = "Enter valid username";


    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(userData.email.trim()))
      errors.email = "Enter valid email";

    const passwordRegex =
      /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/;

    if (!userData.password || !passwordRegex.test(userData.password.trim())) {
      errors.password =
        "Password must be 8-20 chars, include uppercase, lowercase, number & special char";
    }

    if (!userData.rePassword || !passwordRegex.test(userData.rePassword.trim())) {
      errors.rePassword =
        "Password must be 8-20 chars, include uppercase, lowercase, number & special char";
    }

    if ((!errors.password && !errors.rePassword) && userData.password !== userData.rePassword) {
      errors.rePassword = "Passwords do not match";
    }

    setErrors(errors);
    return Object.keys(errors).length === 0;
  };

  const handleAddUser = async () => {
    if (!validate()) return;

    try {

      setIsLoading(true);

      await api.post("/users", userData);
      onSuccess();
      setUserData(initialFormData);
      onClose();

    } catch (err) {
      const { statusCode, errorCode } = err?.response?.data || {};

      if (statusCode === 409) {
        let errors = {};

        if (errorCode === "DUPLICATE_USERNAME")
          errors.userName = "User with this name already exists";

        if (errorCode === "DUPLICATE_EMAIL")
          errors.email = "User with this email already exists";

        setErrors(errors);
      } else {
        console.error("Error adding user:", err);
      }
    } finally {
      setIsLoading(false);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;

    if (name === "userName" && value !== "" && !/^[A-Za-z]*$/.test(value))
      return;

    if ((name === "password" || name === "rePassword") && !/^[A-Za-z\d@$!%*?&]*$/.test(value)) return;

    setUserData((prev) => ({
      ...prev,
      [name]: value,
    }));

    setErrors((prev) => ({ ...prev, [name]: "" }));
  };

  return (
    <div className="fixed inset-0 bg-black/40 flex justify-center items-center z-50">
      <div className="bg-white p-6 rounded-2xl relative w-full max-w-md shadow-xl">
        <button
          onClick={onClose}
          className="absolute p-2 top-3 right-3 text-white bg-red-500 hover:bg-red-600 rounded-full shadow-md transition"
        >
          <XMarkIcon className="h-6 w-6" />
        </button>

        <h2 className="text-xl mb-4 text-center font-semibold">Add User</h2>
        <div className="mb-4">
          <label className="block text-base mb-2 font-medium">
            User Name<span className="text-red-500 ms-0.5">*</span>
          </label>
          <input
            type="text"
            name="userName"
            value={userData.userName}
            onChange={(e) => handleChange(e)}
            className="w-full p-2 mb-1 text-sm border border-gray-300 outline-none focus:ring-2 focus:ring-indigo-600 rounded-lg text-blue-700"
            maxLength={45}
          />
          {errors.userName && (
            <p className="text-red-500 text-sm border-red-700">
              {errors.userName}
            </p>
          )}
        </div>
        <div className="mb-4">
          <label className="block text-base mb-2 font-medium">
            Email<span className="text-red-500 ms-0.5">*</span>
          </label>
          <input
            type="text"
            name="email"
            value={userData.email}
            onChange={(e) => handleChange(e)}
            className="w-full p-2 mb-1 text-sm border border-gray-300 outline-none focus:ring-2 focus:ring-indigo-600 rounded-lg text-blue-700"
            maxLength={45}
          />
          {errors.email && (
            <p className="text-red-500 text-sm border-red-700">
              {errors.email}
            </p>
          )}
        </div>

        <PasswordField
          label={"Password"}
          error={errors.password}
          name={"password"}
          value={userData.password}
          onChange={(e) => handleChange(e)}
        />

        <PasswordField
          label={"Re-enter Password"}
          error={errors.rePassword}
          name={"rePassword"}
          value={userData.rePassword}
          onChange={(e) => handleChange(e)}
        />

        <div className="flex justify-end gap-2 mt-5">
          <button
            className="px-4 py-2 bg-gray-200 hover:bg-gray-300 rounded-lg text-sm"
            onClick={onClose}
          >
            Cancel
          </button>
          <button
            disabled={isLoading}
            className="px-4 py-2 bg-gradient-to-r from-blue-600 to-blue-700 text-white font-semibold rounded-lg p-3 hover:from-blue-700 hover:to-blue-800 transition disabled:opacity-60"
            onClick={handleAddUser}
          >
            {isLoading ? "Adding..." : "Add User"}
          </button>
        </div>
      </div>
    </div>
  );
};

export default AddUser;
