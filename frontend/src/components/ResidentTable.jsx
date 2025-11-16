
import {
  PencilSquareIcon as PencilIcon,
  TrashIcon,
} from "@heroicons/react/24/outline";
import {
  ChatBubbleOvalLeftEllipsisIcon as ChatIcon,
  PhoneIcon,
} from "@heroicons/react/24/solid";

import Pagination from "./Pagination";
import TableSkeleton from "./TableSkeleton";
import formatPhone from "../utils/formatPhone";
import { useSelector } from "react-redux";
import { useState } from "react";
import ResidentMessagesLog from "./ResidentMessagesLog";

const ResidentTable = ({
  residents,
  pageable,
  handlePagination,
  isLoading,
  onUpdate,
  onDelete,
  onSendMessage
}) => {

  const { metaData } = useSelector((state) => state.meta);
  const [messageModalOpen, setMessageModalOpen] = useState(false);
  const [selectedResident, setSelectedResident] = useState(null);

  const handleMessagesLog = (resident) => {
    setMessageModalOpen(true);
    setSelectedResident(resident);
  }


  return (
    <div className="bg-white rounded-lg shadow overflow-x-auto">
      <table className="min-w-full bg-white shadow rounded-lg overflow-hidden ">
        <thead className="bg-blue-900 text-center text-white">
          <tr>
            <th className="px-4 py-3">Id</th>
            <th className="px-4 py-3">Name</th>
            <th className="px-4 py-3">Building</th>
            <th className="px-4 py-3">Flat</th>
            <th className="px-4 py-3">Cellphone</th>
            <th className="px-4 py-3">Tags</th>
            <th className="px-4 py-3">Broadcast</th>
            <th className="px-4 py-3">More</th>
            <th className="px-4 py-3">Messages</th>
          </tr>
        </thead>
        {isLoading ? (
          <TableSkeleton columns={9} rows={10} />
        ) : (
          <tbody className="text-center divide-y divide-gray-200">
            {residents && residents.length > 0 ? (
              residents?.map((resident) => (
                <tr key={resident.id} className="hover:bg-gray-100">
                  <td className="px-4 py-3">{resident.id}</td>
                  <td className="px-4 py-3">{resident.residentName}</td>
                  <td className="px-4 py-3">
                    {metaData?.buildingsList.find(b => b.id === resident.buildingId)?.buildingName || "N/A"}
                  </td>
                  <td className="px-4 py-3">{resident.flatNo}</td>
                  <td className="px-4 py-3">
                    {formatPhone(resident.phoneNumber)}
                  </td>
                  <td className="px-4 py-3">
                    {resident?.tagIds && resident.tagIds.length > 0 ? (
                      <div className="flex justify-center gap-3">
                        {resident.tagIds.map((tagId) => {
                          const tag = metaData?.tagsList?.find(
                            (t) => t.id === tagId
                          );
                          return (
                            <span
                              key={tagId}
                              className="px-3 py-1.5 text-sm rounded-full bg-blue-100 text-blue-800 border border-blue-300"
                            >
                              {tag?.name || "Unknown"}
                            </span>
                          );
                        })}
                      </div>
                    ) : (
                      <span className="px-3 py-1.5 text-sm font-medium rounded-lg bg-gray-100 text-gray-500 border border-gray-300">
                        No Tags
                      </span>
                    )}
                  </td>
                  <td className="p-3 flex items-center justify-center gap-5">
                    <button
                      className="p-2 round-full bg-green-100 hover:bg-green-200 text-green-600"
                      title="Send Text Message"
                      onClick={() => onSendMessage("text", resident)}
                    >
                      <ChatIcon className="h-5 w-5" />
                    </button>
                    <button
                      className="p-2 round-full bg-yellow-100 hover:bg-yellow-200 text-yellow-600"
                      title="Send voice message"
                      onClick={() => onSendMessage("voice", resident)}
                    >
                      <PhoneIcon className="h-5 w-5" />
                    </button>
                  </td>
                  <td className="px-4 py-2 align-middle">
                    <div className="flex items-center justify-center gap-5">
                      <button
                        className="p-3 rounded-full bg-blue-100 hover:bg-blue-200 text-blue-600"
                        title="Edit Resident"
                        onClick={() => onUpdate(resident)}
                      >
                        <PencilIcon className="h-5 w-5" />
                      </button>
                      <button
                        className="p-3 rounded-full bg-red-100 hover:bg-red-200 text-red-600"
                        title="Delete Resident"
                        onClick={() => onDelete(resident)}
                      >
                        <TrashIcon className="h-5 w-5" />
                      </button>
                    </div>
                  </td>
                  <td>
                    <button
                      onClick={() => handleMessagesLog(resident)}
                      className="px-3 py-1.5 text-md font-medium text-blue-700 bg-blue-50 rounded-full hover:bg-blue-100 transition-colors"
                    >
                      View
                    </button>
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td
                  colSpan={9}
                  className="text-center font-semibold text-red-500 px-4 py-4"
                >
                  No residents found
                </td>
              </tr>
            )}
          </tbody>
        )}
      </table>
      {pageable && !isLoading && (
        <Pagination
          handlePagination={handlePagination}
          source={"Residents"}
          pageable={pageable}
        />
      )}

      {messageModalOpen && selectedResident && (
        <ResidentMessagesLog
          onClose={() => setMessageModalOpen(false)}
          selectedResidentId={selectedResident.id}
        />
      )

      }

    </div>
  );
};

export default ResidentTable;
