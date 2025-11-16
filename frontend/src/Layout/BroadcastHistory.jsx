import { Tooltip } from "react-tooltip";
import { useEffect, useState } from "react";
import Pagination from "../components/Pagination";
import formatDateTime from "../utils/formatDateTime";
import TableSkeleton from "../components/TableSkeleton";
import MessagesLog from "../components/MessagesLog";
import api from "../api/api";
import BroadcastMode from "../components/BroadcastMode";
import { DEFAULT_PAGE, DEFAULT_PAGE_SIZE } from "../utils/Pagination";
import BroadcastType from "../components/BroadcastType";

const initialFilter = {
  broadcastMode: "ALL",
  status: "ALL",
  broadcastDate: "",
};

const BroadcastHistory = () => {
  const [broadcasts, setBroadcasts] = useState([]);
  const [pagination, setPagination] = useState({});
  const [filters, setFilters] = useState(initialFilter);
  const [selectedBroadcast, setSelectedBroadcast] = useState(null);
  const [loading, setLoading] = useState(false);
  const [modalOpen, setModalOpen] = useState(false);

  useEffect(() => {
    if (isFiltersDefault()) {
      getBroadcasts(DEFAULT_PAGE, DEFAULT_PAGE_SIZE);
      return;
    };
    
    const timer = setTimeout(() => {
      searchBroadcasts(filters, DEFAULT_PAGE, DEFAULT_PAGE_SIZE);
    }, 400);

    return () => clearTimeout(timer);
  }, [filters]);

  const getBroadcasts = async (pageNumber, pageSize) => {
    setLoading(true);
    const {
      data: { data: broadcastList, page: paginationInfo },
    } = await api.get("/broadcast", {
      params: {
        page: pageNumber,
        size: pageSize,
      },
    });

    setBroadcasts(broadcastList);
    setPagination(paginationInfo || {});
    setLoading(false);
  };

  const searchBroadcasts = async (filters, pageNumber = DEFAULT_PAGE, pageSize = DEFAULT_PAGE_SIZE) => {
    setLoading(true);

    const {
      data: { data: broadcastList, page: paginationInfo },
    } = await api.get("/broadcast/search", {
      params: {
        broadcastMode: filters.broadcastMode,
        status: filters.status,
        broadcastDate: filters.broadcastDate,
        page: pageNumber,
        size: pageSize,
      },
    });
    setBroadcasts(broadcastList);
    setPagination(paginationInfo || {});
    setLoading(false);
  };

  const handlePagination = (pageNumber, pageSize) => {
    if (!isFiltersDefault()) {
      searchBroadcasts(filters, pageNumber, pageSize);
    } else {
      getBroadcasts(pageNumber, pageSize);
    }
  };

  const handleMessagesLog = (broadcast) => {
    setSelectedBroadcast(broadcast);
    setModalOpen(true);
  };

  const handleResetFilters = () => {
    setFilters(initialFilter);
    getBroadcasts(DEFAULT_PAGE, DEFAULT_PAGE_SIZE);
  };

  const handleFilterChange = (e) => {
    const { name, value } = e.target;

    setFilters((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const isFiltersDefault = () => {
    return (
      filters.broadcastMode === initialFilter.broadcastMode &&
      filters.status === initialFilter.status &&
      filters.broadcastDate === initialFilter.broadcastDate
    );
  };

  return (
    <div className="p-3 space-y-6">
      <div className="flex items-center justify-center gap-3 mb-6">
        <h1 className="text-2xl font-bold text-gray-800">Broadcast History</h1>
      </div>
      <div className="flex flex-wrap items-center gap-6">
        <select
          name="broadcastMode"
          value={filters.broadcastMode}
          onChange={handleFilterChange}
          className="border bg-white border-gray-300 rounded-lg px-4 py-3 text-gray-700 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none"
        >
          <option value="ALL">All Methods</option>
          <option value="TEXT_MESSAGE">Text Message</option>
          <option value="VOICE">Voice</option>
        </select>

        <select
          name="status"
          value={filters.status}
          onChange={handleFilterChange}
          className="border bg-white border-gray-300 rounded-lg px-4 py-3 text-gray-700 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none"
        >
          <option value="ALL">All status</option>
          <option value="COMPLETED">Completed</option>
          <option value="IN_PROGRESS">In progress</option>
          <option value="FAILED">Failed</option>
        </select>

        <input
          name="broadcastDate"
          type="date"
          value={filters.broadcastDate}
          onChange={handleFilterChange}
          max={new Date().toISOString().split("T")[0]}
          className="border bg-white border-gray-300 rounded-lg px-4 py-3 text-gray-700 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none"
        />
        {!isFiltersDefault() && (
          <button
            onClick={handleResetFilters}
            className="px-3 py-2 text-sm rounded-md bg-blue-500 hover:bg-blue-600 text-white font-medium border border-gray-300"
          >
            Reset
          </button>
        )}
      </div>

      <div className="bg-white rounded-lg shadow overflow-x-auto">
        <table className="min-w-full bg-white shadow rounded-lg overflow-hidden">
          <thead className="bg-blue-900 text-white uppercase text-sm font-semibold tracking-wider">
            <tr>
              <th className="px-4 py-3">Title</th>
              <th className="px-4 py-3">Type</th>
              <th className="px-4 py-3">Date</th>
              <th className="px-4 py-3">Mode</th>
              <th className="px-4 py-3">Status</th>
              <th className="px-4 py-3">Sent / Total</th>
              <th className="px-4 py-3">Messages</th>
            </tr>
          </thead>
          {loading ? (
            <TableSkeleton columns={6} rows={10} />
          ) : (
            <tbody className="text-center divide-y divide-gray-200">
              {broadcasts && broadcasts.length > 0 ? (
                broadcasts.map((broadcast) => (
                  <tr
                    key={broadcast.id}
                    className="hover:bg-gray-100 font-semibold"
                  >
                    <td className="px-4 py-3 max-w-xs">
                      <div
                        className="truncate text-gray-800"
                        data-tooltip-id={`tooltip-${broadcast.id}`}
                      >
                        {broadcast.title}
                      </div>
                      <Tooltip
                        id={`tooltip-${broadcast.id}`}
                        place="top"
                        content={broadcast.title}
                      />
                    </td>
                    <td className="px-4 py-3">
                      <BroadcastType broadcastType={broadcast.broadcastType} />
                    </td>
                    <td className="px-4 py-3 text-base text-gray-800">
                      {formatDateTime(broadcast.broadcastDateTime)}
                    </td>
                    <td className="px-4 py-2">
                      <BroadcastMode mode={broadcast.broadcastMode} />
                    </td>
                    <td className="px-4 py-3 text-base font-medium">
                      <span
                        className={`px-3 py-1 rounded-full text-md
                        ${broadcast.status === "Completed"
                            ? "bg-green-100 text-green-800 border border-green-300"
                            : broadcast.status === "Failed"
                              ? "bg-red-100 text-red-800 border border-red-300"
                              : "bg-yellow-100 text-yellow-800 border border-yellow-300"
                          }`}
                      >
                        {broadcast.status}
                      </span>
                    </td>
                    <td>
                      <span className="font-medium">{broadcast.totalSent}</span>
                      <span className="text-gray-500 mx-1">/</span>
                      <span className="text-gray-700">
                        {broadcast.totalRecipients}
                      </span>
                    </td>
                    <td>
                      <button
                        onClick={() => handleMessagesLog(broadcast)}
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
                    colSpan={8}
                    className="text-center font-semibold text-red-500 px-4 py-4"
                  >
                    No Broadcasts found
                  </td>
                </tr>
              )}
            </tbody>
          )}
        </table>
        {!loading && (
          <Pagination
            handlePagination={handlePagination}
            source={"Broadcasts"}
            pageable={pagination}
          />
        )}

        {modalOpen && (
          <MessagesLog
            onClose={() => setModalOpen(false)}
            broadcastData={selectedBroadcast}
          />
        )}
      </div>
    </div>
  );
};

export default BroadcastHistory;
