import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import {
  MegaphoneIcon,
  BoltIcon,
  PhoneIcon,
  ChatBubbleOvalLeftIcon as MessageIcon,
} from "@heroicons/react/24/outline";
import BroadcastConfirmationModal from "../components/Modal/BroadcastConfirmationModal";
import { fetchMetaData } from "../store/Metaslice";
import api from "../api/api";

const intialFormData = {
  mode: "text",
  buildingId: "",
  broadcastType: "",
  title: "",
  messageContent: "",
  tagIds: [],
};

const Broadcast = () => {
  const { metaData, loading } = useSelector((state) => state.meta);

  const [broadcastData, setBroadcastData] = useState(intialFormData);

  const [errors, setErrors] = useState({});
  const [residentCount, setResidentCount] = useState(0);
  const [modalOpen, setModalOpen] = useState(false);
  const [modalType, setModalType] = useState("confirm");

  const maxMessageLength = 250;

  const dispatch = useDispatch();

  useEffect(() => {

    if (!metaData || Object.keys(metaData).length === 0) {
      dispatch(fetchMetaData());
    }

  }, [dispatch, metaData]);


  useEffect(() => {
    setBroadcastData(intialFormData);
  }, []);

  const templateMessages = [
    "Water supply maintenance tomorrow 9 AM - 1 PM.",
    "Emergency drill scheduled Friday at 3 PM.",
    "Electricity maintenance on Saturday 2 PM - 4 PM.",
    "Never gonna give you up, never gonna let you down!",
  ];

  const broadcastTypeList = [
    { value: "Maintenance", label: "Maintenance" },
    { value: "Emergency", label: "Emergency" },
    { value: "Utility", label: "Utility" },
    { value: "General", label: "General Info" },
    { value: "Event", label: "Events Notification" },
    { value: "custom", label: "Custom" },
  ];

  const validate = () => {
    let errors = {};

    if (!broadcastData.broadcastType) errors.broadcastType = "Please select a Broadcast Type";

    if (!broadcastData.title) errors.title = "Please enter the broadcast Title";

    if (broadcastData.title.length > 100) 
        errors.title = "Title should be under 100 characters";

    if (!broadcastData.messageContent)
      errors.messageContent = "Please enter the broadcast Message";

    setErrors(errors);
    return Object.keys(errors).length === 0;
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setBroadcastData((prev) => ({ ...prev, [name]: value }));
    setErrors((prev) => ({ ...prev, [name]: "" }));
  };

  const handleTagChange = (selectedTag) => {
    setBroadcastData((prev) => {
      const updatedTags = prev.tagIds.includes(selectedTag)
        ? prev.tagIds.filter((tag) => tag !== selectedTag)
        : [...prev.tagIds, selectedTag];

      return { ...prev, tagIds: updatedTags.slice(0, 2) };
    });
  };

  const handleBroadcastConfirmation = async () => {
    console.log("message sent request send to backend");

    try {

      const endpoint = broadcastData.mode === "voice" ? "voice" : "text-message";

      await api.post(`/broadcast/${endpoint}`, broadcastData);

      setModalType("success");
      
      setTimeout(() => {
        setBroadcastData(intialFormData);
        setModalOpen(false);
      }, 2000);

    } catch(err) {
      console.error("Broadcast send failed:", err);
      setModalType("error");
    }

  };

  const handleSend = async (e) => {
    e.preventDefault();

    if (!validate()) return;

    const buildingId = broadcastData.buildingId;
    const tagIds = broadcastData.tagIds;

    const { data: { data: residentCount } } = await api.get("residents/count", {
      params: {
        buildingId,
        tagIds: tagIds.join(",")
      }
    })

    console.log("residentCount ", residentCount);

    setResidentCount(residentCount);
    setModalType("confirm");
    setModalOpen(true);

    console.log("send broadcast : ", broadcastData);
  };


  if (loading) return <p>Loading...</p>;

  return (
    <div className="flex bg-gradient-to-br from-indigo-200 via-white to-blue-100 p-4 gap-4 h-full">
      {/* Main Form */}
      <div className="flex-1 bg-white/70 backdrop-blur-lg rounded-3xl shadow-xl p-6 border border-gray-100">
        <h2 className="text-3xl font-bold text-gray-800 mb-4 flex items-center gap-2">
          <MegaphoneIcon className="w-8 h-8 text-indigo-600" /> Broadcast
          Message
        </h2>

        <div className="mb-5">
          <label className="block text-base font-semibold mb-3">
            Broadcast Mode
          </label>
          <div className="flex gap-4">
            <button
              name="mode"
              value="text"
              onClick={handleChange}
              className={`flex items-center gap-2 px-5 py-2 rounded-full border transition ${broadcastData.mode === "text"
                  ? "bg-indigo-600 text-white"
                  : "bg-white border-gray-300 text-gray-600 hover:bg-indigo-50"
                }`}
            >
              <MessageIcon className="w-5 h-5" />
              Text
            </button>
            <button
              name="mode"
              value="voice"
              onClick={handleChange}
              className={`flex items-center gap-2 px-5 py-2 rounded-full border transition ${broadcastData.mode === "voice"
                  ? "bg-indigo-600 text-white"
                  : "bg-white border-gray-300 text-gray-600 hover:bg-indigo-50"
                }`}
            >
              <PhoneIcon className="w-5 h-5" />
              Voice
            </button>
          </div>
        </div>

        {/* Building */}
        <div className="mb-5">
          <label className="block text-base font-semibold mb-3">
            Select Building
          </label>
          <select
            name="buildingId"
            value={broadcastData.buildingId}
            onChange={handleChange}
            className="w-full p-3 rounded-xl border border-gray-300 outline-none focus:ring-2 focus:ring-indigo-600 transition-colors"
          >
            <option value="all">All Buildings</option>
            {metaData?.buildingsList?.map((building) => (
              <option key={building.id} value={building.id}>
                {building.buildingName}{" "}
              </option>
            ))}
          </select>
        </div>

        {/* Tags */}
        <div className="mb-5">
          <label className="block text-base font-semibold mb-3">
            Select Tags
          </label>
          <div className="flex flex-wrap gap-2 mb-3">
            {metaData?.tagsList?.map((tag) => (
              <button
                key={tag.id}
                type="button"
                name="tags"
                onClick={() => handleTagChange(tag.id)}
                className={`px-4 py-2 rounded-full border text-sm transition ${broadcastData.tagIds.includes(tag.id)
                    ? "bg-indigo-600 text-white"
                    : "bg-white border-gray-300 text-gray-600 hover:bg-indigo-50"
                  }`}
              >
                {tag.name}
              </button>
            ))}
          </div>
        </div>

        {/* Broadcast Type Toggle */}
        <div className="mb-5">
          <label className="block text-base font-semibold mb-3">
            Broadcast Type
          </label>
          <select
            name="broadcastType"
            value={broadcastData.broadcastType}
            onChange={handleChange}
            className="w-full p-3 rounded-xl border border-gray-300 outline-none focus:ring-2 focus:ring-indigo-600 transition-colors"
          >
            <option value="">Select broadcast type</option>
            {broadcastTypeList.map((type) => (
              <option key={type.value} value={type.value}>
                {type.label}{" "}
              </option>
            ))}
          </select>
          {errors.broadcastType && (
            <p className="text-red-600 text-sm mt-1">{errors.broadcastType}</p>
          )}
        </div>

        <div className="mb-5">
          <label className="block text-base font-semibold mb-3">Title</label>
          <input
            name="title"
            type="text"
            value={broadcastData.title}
            onChange={handleChange}
            placeholder="Type your broadcast title"
            className="w-full p-2 rounded-lg border border-gray-300 outline-none focus:ring-2 focus:ring-indigo-600 "
          />
          {errors.title && (
            <p className="text-red-600 text-sm mt-1">{errors.title}</p>
          )}
        </div>

        {/* Message */}
        <div className="mb-5">
          <label className="block text-base font-semibold mb-3">Message</label>
          <textarea
            name="messageContent"
            value={broadcastData.messageContent}
            onChange={handleChange}
            placeholder="Type your broadcast message..."
            className="w-full p-4 rounded-xl border border-gray-300 outline-none focus:ring-2 focus:ring-indigo-600 h-36 resize-none"
            maxLength={maxMessageLength}
          ></textarea>
          <div className="text-sm text-gray-500 mt-1 text-right">
            {broadcastData.messageContent.length}/{maxMessageLength}
          </div>
          {errors.messageContent && (
            <p className="text-red-600 text-sm mt-1">{errors.messageContent}</p>
          )}
        </div>

        {/* Send Button */}
        <button
          onClick={handleSend}
          className="w-full py-3 rounded-xl bg-gradient-to-r from-indigo-600 to-blue-500 text-white font-medium hover:opacity-90 transition"
        >
          Send Broadcast
        </button>
      </div>

      <div className="w-1/3 bg-white/70 backdrop-blur-lg rounded-3xl shadow-xl p-8 border border-gray-100 sticky top-8 h-fit">
        <h3 className="text-2xl font-semibold mb-4 text-gray-800 flex items-center gap-2">
          <BoltIcon className="w-6 h-6 text-yellow-500" /> Quick Templates
        </h3>
        <div className="space-y-3">
          {templateMessages.map((template, idx) => (
            <div
              key={idx}
              name="messageContent"
              onClick={() =>
                setBroadcastData((prev) => ({
                  ...prev,
                  messageContent: template,
                }))
              }
              className="p-4 bg-gray-50 hover:bg-indigo-50 rounded-xl border border-gray-200 cursor-pointer transition shadow-sm"
            >
              {template}
            </div>
          ))}
        </div>
      </div>

      <BroadcastConfirmationModal
        isOpen={modalOpen}
        onClose={() => setModalOpen(false)}
        onConfirm={handleBroadcastConfirmation}
        broadcastModalType={modalType}
        residentCount={residentCount}
      />
    </div>
  );
};

export default Broadcast;
