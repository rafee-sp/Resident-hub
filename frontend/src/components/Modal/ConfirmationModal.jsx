import { XMarkIcon } from "@heroicons/react/24/outline";

const ConfirmationModal = ({ handleClose, onConfirm, title, content }) => {
  return (
    <div
      className="fixed inset-0 flex justify-center items-center bg-black/30 backdrop-blur-xs bg-opacity-40 z-50"
      role="dialog"
      aria-modal="true"
    >
      <div className="bg-white p-8 rounded-3xl relative w-full max-w-lg shadow-xl">
        <button
          onClick={handleClose}
          className="absolute p-2 top-3 right-3 text-white bg-red-500 hover:bg-red-600 rounded-full shadow-md-transition"
        >
          <XMarkIcon className="h-6 w-6" />
        </button>
        {title && (
          <h2 className="text-2xl font-semibold mb-10 text-center">{title}</h2>
        )}
        <div className="max-w-md mx-auto mb-10">
          <p className="text-xl text-gray-700 text-center">{content}</p>
        </div>

        <div className="flex justify-end gap-4 mt-6">
          <button
            className="px-4 py-2 bg-gray-200 hover:bg-gray-300 rounded-lg text-sm"
            onClick={handleClose}
          >
            Cancel
          </button>
          <button
            className="px-4 py-2 bg-gradient-to-r from-blue-600 to-blue-700 text-white font-semibold rounded-lg p-3 hover:from-blue-700 hover:to-blue-800 transition disabled:opacity-60"
            onClick={onConfirm}
          >
            Confirm
          </button>
        </div>
      </div>
    </div>
  );
};

export default ConfirmationModal;
