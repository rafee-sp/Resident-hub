import { XMarkIcon } from "@heroicons/react/24/outline";
import Pagination from "./Pagination";
import { useEffect, useState } from "react";
import TableSkeleton from "./TableSkeleton";
import api from "../api/api";
import formatDateTime from "../utils/formatDateTime";
import MessageStatus from "./MessageStatus";
import { Tooltip } from "react-tooltip";
import { DEFAULT_PAGE, DEFAULT_PAGE_SIZE } from "../utils/Pagination";

const MessagesLog = ({ onClose, broadcastData }) => {

  const [broadcast, setBroadcast] = useState(broadcastData || null);
  const [messageLogs, setMessageLogs] = useState([]);
  const [pagination, setPagination] = useState({});
  const [showFailedOnly, setShowFailedOnly] = useState(false);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!broadcastData) return;
    setBroadcast(broadcastData);
    getMessages(broadcastData.id, DEFAULT_PAGE, DEFAULT_PAGE_SIZE);
  }, [broadcastData?.id, showFailedOnly]);

  const getMessages = async (broadcastId, page = 0, size = 10) => {
    setLoading(true);

    const params = { page: page, size: size };

    if (showFailedOnly){
      params.status = "Failed";
    }

    const {
      data: { data: messagesList, page: pageable },
    } = await api.get(`/broadcast/${broadcastId}/messages`, {params});
    console.log(messagesList);
    setMessageLogs(messagesList);
    setPagination(pageable);
    setLoading(false);
  };

  const handlePagination = (pageNumber, pageSize) => {  
    getMessages(broadcast.id, pageNumber, pageSize);
  };


  return (
    <div className="fixed inset-0 bg-black/30 backdrop-blur-xs bg-opacity-40 flex items-center justify-center z-50">
      <div className="bg-white rounded-xl shadow-lg w-full max-w-6xl h-[90vh] flex flex-col">
        {/* Header */}
        <div className="flex items-center px-6 py-4 border-b bg-gray-50 relative">
          <h2 className="flex-1 text-center text-lg font-bold text-gray-800">
            Message Logs
          </h2>

          <button
            onClick={onClose}
            className="p-2 text-white bg-red-500 hover:bg-red-600 rounded-full shadow-md transition"
          >
            <XMarkIcon className="h-5 w-5" />
          </button>
        </div>

        {/* Broadcast Info */}
        <div className="grid grid-cols-3 gap-6 px-6 py-4 border-b bg-white text-md">
          <div className="col-span-2">
            <span className="block text-gray-500 font-medium">Message</span>
            <p className="text-gray-800">{broadcast.message}</p>
          </div>

          <div className="flex flex-col justify-center text-right">
            <p
              className={`text-lg font-bold ${
                broadcast.status === "Completed"
                  ? "text-green-600"
                  : broadcast.status === "Failed"
                  ? "text-red-600"
                  : "text-yellow-600"
              }`}
            >
              {broadcast.status}
            </p>
            <span className="text-gray-500 text-lg">
              {broadcast.totalSent} sent, {broadcast.totalFailed}{" "}
              failed
            </span>
            <span className="text-gray-500 text-lg mt-1">
              Filters: {broadcast?.building === "ALL" ? "All buildings" : broadcast.building}{" "}
              {broadcast.tags?.length > 0
                ? `| Tags: ${broadcast.tags}`
                : ""}
            </span>
          </div>
        </div>

        <div className="px-3 py-4 ms-4 bg-gray-50 flex items-center justify-between">
          <label className="flex items-center gap-3 text-xl font-medium text-gray-700 cursor-pointer">
            <input
              type="checkbox"
              checked={showFailedOnly}
              onChange={() => setShowFailedOnly(!showFailedOnly)}
              className="w-5 h-5 scale-125 accent-red-500 cursor-pointer"
            />
            Show only failed
          </label>
        </div>

        {/* Table */}
        <div className="bg-white shadow overflow-x-auto">
          <table className="min-w-full bg-white shadow  overflow-hidden">
            <thead className="bg-blue-900 text-center text-white text-lg z-10">
              <tr>
                <th className="px-4 py-2 w-20 whitespace-nowrap text-center">
                  Resident Id
                </th>
                <th className="px-4 py-2 whitespace-nowrap">Resident Name</th>
                <th className="px-4 py-2 w-2/5">Message</th>
                <th className="px-4 py-2 w-1/5">Date / Time</th>
                <th className="px-4 py-2 w-1/6">Status</th>
              </tr>
            </thead>
            {loading ? (
              <TableSkeleton columns={5} rows={10} />
            ) : (
              <tbody className="text-center divide-y divide-gray-200 text-lg">
               {messageLogs && messageLogs.length > 0 ? (
                messageLogs.map((message) => (
                  <tr key={message.id} className="hover:bg-gray-50">
                    <td className="px-4 py-3">{message.residentId}</td>
                    <td className="px-4 py-3">{message.residentName}</td>
                    <td className="px-4 py-2 max-w-xs">
                      <div
                        className="truncate text-gray-800"
                        data-tooltip-id={`tooltip-${message.id}`}
                      >
                        {message.message}
                      </div>
                      <Tooltip
                        id={`tooltip-${message.id}`}
                        place="top"
                        content={message.message}
                      />
                    </td>
                    <td className="px-4 py-2">
                      {formatDateTime(message.createdAt)}
                    </td>
                    <td>
                      <MessageStatus status={message.messageStatus} />
                    </td>
                  </tr>
                ))
               ) : (
                <tr>
                  <td
                    colSpan={8}
                    className="text-center font-semibold text-red-500 px-4 py-4"
                  >
                    No Messages found
                  </td>
                </tr>
              )}
              </tbody>
            )}
          </table>
        </div>

        {(messageLogs && messageLogs.length > 0 && !loading ) && (
          <Pagination
            handlePagination={handlePagination}
            pageable={pagination}
            source={"messages"}
          />
        )}
      </div>
    </div>
  );
};

export default MessagesLog;
