
 const tagColors = {
    maintenance: "bg-yellow-100 text-yellow-800 border border-yellow-300", // tools, caution
    emergency: "bg-red-100 text-red-800 border border-red-300", // urgent, danger
    security: "bg-purple-100 text-purple-800 border border-purple-300", // authority, vigilance
    weather: "bg-sky-100 text-sky-800 border border-sky-300", // sky = weather
    events: "bg-pink-100 text-pink-800 border border-pink-300", // festive, social
    general: "bg-blue-100 text-blue-800 border border-blue-300", // neutral info
    default: "bg-gray-100 text-gray-600 border border-gray-300", // fallback
  };

const BroadcastType = ({broadcastType}) => {
  return (
    <div className="flex justify-center gap-3">
      <span
        className={`px-3 py-1.5 text-sm rounded-full ${
          tagColors[broadcastType?.toLowerCase()] || tagColors.default
        }`}
      >
        {broadcastType}
      </span>
    </div>
  );
};

export default BroadcastType;
