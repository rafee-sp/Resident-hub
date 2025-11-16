import { useState, useEffect } from "react";
import { XMarkIcon } from "@heroicons/react/24/outline";
import { useSelector } from "react-redux";
import api from "../../api/api";

const intialFormData = {
  residentName: "",
  phoneNumber: "",
  buildingId: null,
  flatNo: "",
  tagIds: [],
};

const ResidentFormModal = ({ handleClose, resident, onSuccess }) => {
  // Fetch metadata from Redux store
  const { metaData } = useSelector((state) => state.meta);

  const [formData, setFormData] = useState(intialFormData);
  const [errors, setErrors] = useState({});
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    if (resident) {

      setFormData({
        residentName: resident.residentName || "",
        phoneNumber: resident.phoneNumber || "",
        buildingId: resident.buildingId || null,
        flatNo: resident.flatNo || "",
        tagIds: resident.tagIds || [],
      });
    } else {
      setFormData(intialFormData);
    }
  }, [resident]);

  const validate = () => {
    let errors = {};

    if (!/^[A-Za-z\s]+$/.test(formData.residentName.trim()))
      errors.residentName = "Enter valid name";

    if (!/^\d{10}$/.test(formData.phoneNumber.trim()))
      errors.phoneNumber = "Enter valid phone number";

    if (!formData.buildingId ) errors.building = "Please select a building";

    if (!formData.flatNo.trim()) errors.flatNo = "Enter valid flat number";

    setErrors(errors);
    return Object.keys(errors).length === 0;
  };

  const handleSaveResident = async () => {
    if (!validate()) return;

    try {
      setIsLoading(true);

      if (resident) {
        await api.put(`/residents/${resident.id}`, formData);

        onSuccess({ ...formData, id: resident.id });
      } else {
        await api.post("/residents", formData);
        onSuccess();
      }
    } catch (err) {
      const { statusCode, errorCode } = err?.response?.data || {};

      if (statusCode === 409) {
        let errors = {};

        if (errorCode === "DUPLICATE_PHONE")
          errors.phoneNumber = "Resident with this phone number already exists";

        if (errorCode === "DUPLICATE_FLAT")
          errors.flatNo = "Resident is already registered for this Flat";

        setErrors(errors);
      } else {
        console.log("Error saving resident:", err);
      }
    } finally {
      setIsLoading(false);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;

    if (name === "residentName" && value !== "" && !/^[A-Za-z\s]+$/.test(value))
      return;

    if (name === "phoneNumber" && !/^\d*$/.test(value)) return;

    setFormData((prev) => ({
    ...prev,
    [name]: name === "buildingId"
      ? (value ? Number(value) : null) 
      : value,
  }));
    setErrors((prev) => ({ ...prev, [name]: "" }));
  };

  const handleTagChange = (selectedTag) => {
    setFormData((prev) => {
      const updatedTags = prev.tagIds.includes(selectedTag)
        ? prev.tagIds.filter((tag) => tag !== selectedTag)
        : [...prev.tagIds, selectedTag];

      return { ...prev, tagIds: updatedTags.slice(0, 2) };
    });
  };

  const handleModalClose = () => {
    setFormData(intialFormData);
    setErrors({});
    handleClose();
  };

  // if (loading) return <p>Loading...</p>; TODO : need to check

  return (
    <div className="fixed inset-0 bg-black/40 flex justify-center items-center z-50">
      <div className="bg-white p-6 rounded-2xl relative w-full max-w-md shadow-xl">
        <button
          onClick={handleModalClose}
          className="absolute p-2 top-3 right-3 text-white bg-red-500 hover:bg-red-600 rounded-full shadow-md transition"
        >
          <XMarkIcon className="h-6 w-6" />
        </button>

        <h2 className="text-xl mb-4 text-center font-semibold">
          {resident ? "Update Resident" : "Add Resident"}
        </h2>

        <label className="block text-base mb-2 font-medium">
          Name<span className="text-red-500 ms-0.5">*</span>
        </label>
        <input
          type="text"
          name="residentName"
          value={formData.residentName}
          onChange={(e) => handleChange(e)}
          className="w-full p-2 mb-1 text-sm border border-gray-300 outline-none focus:ring-2 focus:ring-indigo-600 rounded-lg text-blue-700"
          maxLength={45}
        />
        {errors.residentName && (
          <p className="text-red-500 text-sm border-red-700">
            {errors.residentName}
          </p>
        )}

        <label className="block text-base mt-3 mb-2 font-medium">
          Phone Number<span className="text-red-500 ms-0.5">*</span>
        </label>
        <input
          type="text"
          name="phoneNumber"
          value={formData.phoneNumber}
          onChange={handleChange}
          className="w-full p-2 mb-1 text-base border border-gray-300 outline-none focus:ring-2 focus:ring-indigo-600 rounded-lg text-blue-700"
          maxLength={10}
        />
        {errors.phoneNumber && (
          <p className="text-red-500 text-sm">{errors.phoneNumber}</p>
        )}

        <label className="block text-base mt-3 mb-2 font-medium">
          Building<span className="text-red-500 ms-0.5">*</span>
        </label>
        <select
          name="buildingId"
          value={formData.buildingId}
          onChange={handleChange}
          className="w-full p-2 mb-1 text-base border border-gray-300 outline-none focus:ring-2 focus:ring-indigo-600 rounded-lg text-blue-700"
        >
          <option value="">Select Building</option>
          {metaData?.buildingsList?.map((building) => (
            <option key={building.id} value={building.id}>
              {building.buildingName}{" "}
            </option>
          ))}
        </select>
        {errors.building && (
          <p className="text-red-500 text-sm">{errors.building}</p>
        )}

        <label className="block text-base mt-3 mb-2 font-medium">
          Flat Number<span className="text-red-500 ms-0.5">*</span>
        </label>
        <input
          type="text"
          name="flatNo"
          value={formData.flatNo}
          onChange={handleChange}
          className="w-full p-2 mb-1 text-base border border-gray-300 outline-none focus:ring-2 focus:ring-indigo-600 rounded-lg text-blue-700"
          maxLength={10}
        />
        {errors.flatNo && (
          <p className="text-red-500 text-sm">{errors.flatNo}</p>
        )}

        <label className="block text-base mt-3 mb-2 font-medium">Tags</label>
        <div className="flex flex-wrap gap-2 mb-3">
          {metaData?.tagsList?.map((tag) => (
            <button
              key={tag.id}
              type="button"
              name="tags"
              onClick={() => handleTagChange(tag.id)}
              className={`px-3 py-1 rounded-full text-base border transition-colors ${
                formData?.tagIds?.includes(tag.id)
                  ? "bg-blue-600 text-white border-blue-600"
                  : "bg-gray-200 text-gray-700 border-gray-300 hover:bg-blue-600 hover:text-white"
              }`}
            >
              {tag.name}
            </button>
          ))}
        </div>

        <div className="flex justify-end gap-2 mt-5">
          <button
            className="px-4 py-2 bg-gray-200 hover:bg-gray-300 rounded-lg text-sm"
            onClick={handleModalClose}
          >
            Cancel
          </button>
          <button
            onClick={handleSaveResident}
            disabled={isLoading}
            className="px-4 py-2 bg-gradient-to-r from-blue-600 to-blue-700 text-white font-semibold rounded-lg p-3 hover:from-blue-700 hover:to-blue-800 transition disabled:opacity-60"
          >
            {isLoading
              ? resident
                ? "Updating..."
                : "Adding..."
              : resident
              ? "Update Resident"
              : "Add Resident"}
          </button>
        </div>
      </div>
    </div>
  );
};

export default ResidentFormModal;
