import { EyeIcon, EyeSlashIcon, XMarkIcon } from "@heroicons/react/24/outline";
import { useEffect, useState } from "react";
import PasswordField from "../PasswordField";
import api from "../../api/api";

const initialFormData = {
  currentPassword: "",
  newPassword: "",
  reNewPassword: "",
  userId: null,
};

const ResetPassword = ({ onClose, selectedUserId }) => {
  const [userData, setUserData] = useState({
    ...initialFormData,
    userId: selectedUserId ?? null,
  });

  const [errors, setErrors] = useState({});
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    setUserData((prev) => ({ ...prev, userId: selectedUserId ?? null }));
  }, [selectedUserId]);

  const validate = () => {

    let errors = {};

    const { newPassword, reNewPassword, currentPassword } = userData;

    ["newPassword", "reNewPassword", "currentPassword"].forEach((key) => {
      if (!isPasswordValid(userData[key])) {
        errors[key] =
          "Password must be 8–20 chars, include uppercase, lowercase, number & special char";
      }
    });

    if (!errors.newPassword && !errors.reNewPassword && newPassword !== reNewPassword) {
      errors.reNewPassword = "Passwords do not match";
    }

    setErrors(errors);
    return Object.keys(errors).length === 0;
  };

  const isPasswordValid = (password) => {

    const passwordRegex =
      /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/;

    return password && passwordRegex.test(password.trim())

  }

  const handleResetPassword = async () => {

    if (!validate()) return;

    try {

      setIsLoading(true);

      await api.put(`/users/${userData.userId}/reset-password`, userData);
      setIsLoading(false);
      onClose();

    } catch (err) {
      const { statusCode, errorCode } = err?.response?.data || {};

      if (statusCode === 400) {
        let errors = {};

        if (errorCode === "SAME_NEW_PASSWORD")
          errors.newPassword = "Old password and new password cannot be same";

        if (errorCode === "INVALID_CURRENT_PASSWORD")
          errors.currentPassword = "Current password is incorrect";

        setErrors(errors);
      }

    } finally {
      setIsLoading(false);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;

    if (!/^[A-Za-z\d@$!%*?&]*$/.test(value)) return;

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

        <h2 className="text-xl mb-4 text-center font-semibold">Reset Password</h2>


        <PasswordField
          label={"New Password"}
          error={errors.newPassword}
          name={"newPassword"}
          value={userData.newPassword}
          onChange={(e) => handleChange(e)}
        />

        <PasswordField
          label={"Re-enter New Password"}
          error={errors.reNewPassword}
          name={"reNewPassword"}
          value={userData.reNewPassword}
          onChange={(e) => handleChange(e)}
        />


        <PasswordField
          label={"Current Password"}
          error={errors.currentPassword}
          name={"currentPassword"}
          value={userData.currentPassword}
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
            onClick={handleResetPassword}
          >
            {isLoading ? "Resetting..." : "Reset"}
          </button>
        </div>
      </div>
    </div>
  );
};

export default ResetPassword;
