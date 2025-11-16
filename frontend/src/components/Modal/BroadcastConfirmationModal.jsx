import { useEffect, useState } from "react";

const BroadcastConfirmationModal = ({
  isOpen,
  onClose,
  onConfirm,
  broadcastModalType,
  residentCount,
}) => {

  const [modalType, setModalType] = useState(broadcastModalType ?? "confirm");
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (broadcastModalType) {
      setLoading(false);
      setModalType(broadcastModalType);
    }
  }, [broadcastModalType]);

  const handleConfirm = async () => {
    setLoading(true);
    onConfirm();
  };

  const handleClose = () => {
    
    setLoading(false);    
    onClose();
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 flex justify-center items-center bg-black/30 backdrop-blur-xs bg-opacity-40 z-50">
      <div className="bg-white rounded-xl p-6 w-full max-w-md shadow-lg">
        {modalType === "confirm" && (
          <div className="animate-fadeIn">            
            <div className="flex items-center gap-3 mb-6">
              <h2 className="text-2xl font-bold text-gray-800">
                Confirm Broadcast
              </h2>
            </div>            
            <p className="mb-8 p-3 text-lg text-gray-700 leading-relaxed">
              This message will be sent to{" "}
              <span className="font-semibold text-indigo-600 text-xl">
                {residentCount}
              </span>{" "}
              residents.
            </p>          
            <div className="flex justify-end gap-4 pt-4">
              <button
                className="px-5 py-2 rounded-lg bg-gray-200 hover:bg-gray-300 transition-colors duration-200"
                onClick={handleClose}
              >
                Cancel
              </button>
              <button
                className="px-5 py-2 rounded-lg bg-indigo-600 text-white hover:bg-indigo-700 shadow-md hover:shadow-lg transition-all duration-200 disabled:opacity-50"
                onClick={handleConfirm}
                disabled={loading}
              >
                {loading ? "Sending..." : "Confirm"}
              </button>
            </div>
          </div>
        )}

        {modalType === "success" && (
          <div className="animate-fadeIn pt-6">
            <p className="mb-8 text-lg text-gray-700 leading-relaxed">
              Your broadcast has started. Messages are being delivered in the
              background. Check the Broadcast Logs for updates.
            </p>

            <div className="flex justify-end">
              <button
                className="px-5 py-2 rounded-lg bg-green-600 text-white hover:bg-green-700 shadow-md hover:shadow-lg transition-all duration-200"
                onClick={handleClose}
              >
                Close
              </button>
            </div>
          </div>
        )}

        {modalType === "error" && (
          <div className="animate-fadeIn pt-6">
            <p className="mb-8 text-lg text-red-500 leading-relaxed">
              Broadcast failed to start. Pease try again later or reach out to
              support if it persists.
            </p>

            <div className="flex justify-end">
              <button
                className="px-5 py-2 rounded-lg bg-blue-600 text-white hover:bg-blue-700 shadow-md hover:shadow-lg transition-all duration-200"
                onClick={handleClose}
              >
                Close
              </button>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default BroadcastConfirmationModal;
