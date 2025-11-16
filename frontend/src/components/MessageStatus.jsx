const MessageStatus = ({status}) => {

    console.log("status:", status);

  return (
    <span
      className={`px-3 py-1 rounded-full text-lg
                        ${
                          status === "Delivered"
                            ? "bg-green-100 text-green-800 border border-green-300"
                            : status === "Failed" || status === "No Answer"
                            ? "bg-red-100 text-red-800 border border-red-300"
                            : "bg-yellow-100 text-yellow-800 border border-yellow-300"
                        }`}
    >
      {status}
    </span>
  );
};

export default MessageStatus;
