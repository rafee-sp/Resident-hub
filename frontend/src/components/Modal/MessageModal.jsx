import { useState } from "react";
import { motion } from "framer-motion";
import {
  XMarkIcon,
  PaperAirplaneIcon,
  CheckCircleIcon,
} from "@heroicons/react/24/outline";
import { PaperAirplaneIcon as PaperAirplaneSolid } from "@heroicons/react/24/solid";
import api from "../../api/api";

const MessageModal = ({ onSuccess, onClose, messageType, resident }) => {
  const [messageContent, setMessageContent] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [isSending, setIsSending] = useState(false);
  const maxMessageLength = 250;

  const handleSend = async () => {
    setIsSending(true);
    setErrorMessage("");
    try {

      const endpoint = messageType === "voice" ? "voice" : "text-message";

      await api.post(`/message/${endpoint}`, {
        residentId: resident.id,
        messageContent: messageContent,
      });
      setSuccessMessage("Message sent successfully");
      setTimeout(() => {
        setSuccessMessage("");
        setMessageContent("");
        setIsSending(false);
        onSuccess();
      }, 1500);
    } catch (err) {
      const { message } = err?.response?.data || "Failed to send message. Please try again";
      setErrorMessage(message);
      setIsSending(false);
    }
  };

  const handleClose = () => {
    setMessageContent("");
    setSuccessMessage("");
    setIsSending(false);
    onClose();
  };

  return (
    <div className="fixed inset-0 flex items-center justify-center bg-black/30 backdrop-blur-xs bg-opacity-40 z-50">
      <div className="bg-white rounded-2xl p-6 w-full max-w-md shadow-xl relative">
        <button
          className="absolute p-2 top-3 right-3 text-white bg-red-500 hover:bg-red-600 rounded-full shadow-md transition"
          onClick={handleClose}
        >
          <XMarkIcon className="h-6 w-6" />
        </button>
        <h2 className="text-lg font-semibold mb-4">
          {messageType === "voice" ? "Send voice message" : "Send Text message"}
        </h2>
        <p className="text-sm text-gray-500 mb-2">
          You can use <code>{"{name}"}</code> or <code>{"{flat}"}</code> to
          include resident details
        </p>
        <textarea
          className="w-full min-h-[120px] p-3 rounded-xl border border-gray-300 
                 shadow-sm resize-none outline-none 
                 focus:ring-2 focus:ring-blue-400 focus:border-blue-400
                 transition duration-200"
          rows="4"
          value={messageContent}
          onChange={(e) => setMessageContent(e.target.value)}
          placeholder="Type your message here..."
          maxLength={maxMessageLength}
        ></textarea>
        <div className="text-sm text-gray-500 mt-1 text-right">
          {messageContent.length}/{maxMessageLength}
        </div>
        {successMessage && (
          <div className="flex items-center justify-center gap-2 mt-3 p-3 rounded-lg bg-green-50 border border-green-300 text-green-700 text-sm">
            <CheckCircleIcon className="h-5 w-5 flex-shrink-0" />
            <span>{successMessage}</span>
          </div>
        )}
        {errorMessage && (
          <div className="flex items-center justify-center  gap-2 mt-3 p-3 rounded-lg bg-red-50 border border-red-300 text-red-700 text-sm">
            <XMarkIcon className="h-5 w-5 flex-shrink-0" />
            <span>{errorMessage}</span>
          </div>
        )}
        <div className="mt-4 flex justify-end gap-3">
          <button
            disabled={isSending}
            className="px-4 py-2 rounded-lg bg-gray-200 hover:bg-gray-300"
            onClick={handleClose}
          >
            Cancel
          </button>
          <motion.button
            disabled={!messageContent.trim() || isSending}
            className={`px-4 py-2 rounded-lg flex items-center gap-2 transition-all ${!messageContent.trim()
              ? "bg-gray-300 text-gray-500 cursor-not-allowed"
              : "bg-blue-600 text-white hover:bg-blue-700"
              }`}
            onClick={() => handleSend(messageContent)}
            whileTap={{ scale: 0.9 }}
          >
            <motion.div
              className={`flex items-center justify-center rounded-full p-1.5 ${!messageContent.trim() ? "" : " bg-blue-600 "
                }`}
              animate={
                isSending
                  ? {
                    x: [0, 30, 100],
                    y: [0, -20, -60],
                    rotate: [-20, -10, -30],
                    opacity: [1, 1, 0],
                    scale: [1, 1.1, 0.8],
                  }
                  : {
                    x: 0,
                    y: 0,
                    rotate: -20,
                    opacity: 2,
                    scale: 1,
                  }
              }
              transition={{
                duration: 1.5,
                ease: "easeInOut",
              }}
            >
              <PaperAirplaneSolid className="h-5 w-5 text-white" />
            </motion.div>
          </motion.button>
        </div>
      </div>
    </div>
  );
};

export default MessageModal;
