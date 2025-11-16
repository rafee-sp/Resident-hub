import { Tooltip } from "react-tooltip";
import { useEffect, useState } from "react";
import Pagination from "../components/Pagination";
import formatDateTime from "../utils/formatDateTime";
import TableSkeleton from "../components/TableSkeleton";
import api from "../api/api";
import { DEFAULT_PAGE, DEFAULT_PAGE_SIZE } from "../utils/Pagination";

const initialFilter = {
  actionType: "",
  activityDate: "",
};

const Activity = () => {
  const [activities, setActivities] = useState([]);
  const [pagination, setPagination] = useState({});
  const [filters, setFilters] = useState(initialFilter);
  const [loading, setLoading] = useState(false);

  const actionTypes = [
    { "value": "RESIDENT_CREATE", "label": "Resident Created" },
    { "value": "RESIDENT_UPDATE", "label": "Resident Updated" },
    { "value": "RESIDENT_DELETE", "label": "Resident Deleted" },
    { "value": "VOICE_MESSAGE_SEND", "label": "Voice Message Sent" },
    { "value": "TEXT_MESSAGE_SEND", "label": "Text Message Sent" },
    { "value": "BROADCAST", "label": "Broadcast" },
    { "value": "USER_CREATION", "label": "User Created" },
    { "value": "USER_DELETION", "label": "User Deleted" },
    { "value": "PASSWORD_RESET", "label": "Password Reset" },
    { "value": "TEXT_MESSAGE_CALLBACK", "label": "Text Message Callback" },
    { "value": "VOICE_MESSAGE_CALLBACK", "label": "Voice Message Callback" }
  ]

  useEffect(() => {
    if (isFiltersDefault()) {
      getActivities(DEFAULT_PAGE, DEFAULT_PAGE_SIZE);
      return;
    };

    const timeout = setTimeout(() => {
      filterActivities(filters, DEFAULT_PAGE, DEFAULT_PAGE_SIZE);
    }, 400);

    return () => clearTimeout(timeout);

  }, [filters]);

  const getActivities = async (pageNumber, pageSize) => {
    setLoading(true);
    const {
      data: { data: activityList, page: paginationInfo },
    } = await api.get("/activity", {
      params: {
        page: pageNumber,
        size: pageSize,
      },
    });

    setActivities(activityList);
    setPagination(paginationInfo || {});
    setLoading(false);
  };

  const filterActivities = async (filters, pageNumber = 0, pageSize = 10) => {
    setLoading(true);

    const {
      data: { data: broadcastList, page: paginationInfo },
    } = await api.get("/activity/search", {
      params: {
        actionType: filters.actionType,
        activityDate: filters.activityDate,
        page: pageNumber,
        size: pageSize,
      },
    });
    setActivities(broadcastList);
    setPagination(paginationInfo || {});
    setLoading(false);
  };

  const handlePagination = (pageNumber, pageSize) => {
    if (!isFiltersDefault()) {
      filterActivities(filters, pageNumber, pageSize);
    } else {
      getActivities(pageNumber, pageSize);
    }
  };

  const handleFilterChange = (e) => {
    const { name, value } = e.target;

    setFilters((prev) => ({
      ...prev,
      [name]: value,
    }));
  };


  const handleResetFilters = () => {
    setFilters(initialFilter);     
  };

  const isFiltersDefault = () => {
    return (
      filters.actionType === initialFilter.actionType &&
      filters.activityDate === initialFilter.activityDate
    );
  };

  return (
    <div className="p-3 space-y-6">
      <div className="flex items-center justify-center gap-3 mb-6">
        <h1 className="text-2xl font-bold text-gray-800">Activity</h1>
      </div>
      <div className="flex flex-wrap items-center gap-6">
        <select
          name="actionType"
          value={filters.actionType}
          onChange={handleFilterChange}
          className="border bg-white border-gray-300 rounded-lg px-4 py-3 text-gray-700 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none"
        >
          <option value="">All Actions</option>
          {actionTypes?.map((type) => (

            <option key={type.value} value={type.value}>{type.label}</option>

          ))}
        </select>

        <input
          name="activityDate"
          type="date"
          value={filters.activityDate}
          onChange={handleFilterChange}
          max={new Date().toLocaleDateString("en-CA")}
          className="border bg-white border-gray-300 rounded-lg px-4 py-3 text-gray-700 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none"
        />
        {!isFiltersDefault() && (
          <button className="px-3 py-2 text-sm rounded-md bg-blue-500 hover:bg-blue-600 text-white font-medium border border-gray-300"
            onClick={handleResetFilters}>
            Reset
          </button>
        )}
      </div>

      <div className="bg-white rounded-lg shadow overflow-x-auto">
        <table className="min-w-full bg-white shadow rounded-lg overflow-hidden">
          <thead className="bg-blue-900 text-center text-white">
            <tr>
              <th className="px-4 py-3 w-1/5">Date/Time</th>
              <th className="px-4 py-3 w-1/6">Type</th>
              <th className="px-4 py-3 w-2/5">Description</th>
              <th className="px-4 py-3 w-1/6">Performed By</th>
            </tr>
          </thead>
          {loading ? (
            <TableSkeleton columns={4} rows={10} />
          ) : (
            <tbody className="text-center divide-y divide-gray-200 text-lg">
              {activities && activities.length > 0 ? (
                activities.map((activity) => (
                  <tr
                    key={activity.id}
                    className="hover:bg-gray-100 font-semibold"
                  >
                    <td className="px-5 py-4 text-base text-gray-800">
                      {formatDateTime(activity.createdAt)}
                    </td>
                    <td className="px-5 py-4">{activity.actionType}</td>
                    <td className="px-5 py-4 max-w-xs">
                      <div
                        className="truncate text-gray-800"
                        data-tooltip-id={`tooltip-${activity.id}`}
                      >
                        {activity.description}
                      </div>
                      <Tooltip
                        id={`tooltip-${activity.id}`}
                        place="top"
                        content={activity.description}
                      />
                    </td>
                    <td className="px-5 py-4">{activity.performedBy}</td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td
                    colSpan={8}
                    className="text-center font-semibold text-red-500 px-4 py-4"
                  >
                    No Activities found
                  </td>
                </tr>
              )}
            </tbody>
          )}
        </table>
        {!loading && (
          <Pagination
            handlePagination={handlePagination}
            source={"Activities"}
            pageable={pagination}
          />
        )}
      </div>
    </div>
  );
};

export default Activity;
