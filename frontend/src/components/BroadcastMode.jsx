import {
  ChatBubbleOvalLeftEllipsisIcon as ChatIcon,
  PhoneIcon,
} from "@heroicons/react/24/solid";

const BroadcastMode = ({ mode }) => {

  return (
    <>
      {mode && mode.toLowerCase() === "text" ? (
        <div className="inline-flex items-center justify-center p-2 rounded-full bg-green-100 text-green-600">
          <ChatIcon className="w-5 h-5" />
        </div>
      ) : (
        <div className="inline-flex items-center justify-center p-2 rounded-full bg-yellow-100 text-yellow-600">
          <PhoneIcon className="h-5 w-5" />
        </div>
      )}
    </>
  );
};

export default BroadcastMode;
