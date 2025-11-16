import {
  BellIcon,
  ChatBubbleLeftRightIcon,
  MegaphoneIcon,
  PhoneIcon,
  UserGroupIcon,
} from "@heroicons/react/24/outline";
import { useEffect, useState } from "react";
import Greeting from "../components/Greeting";
import api from "../api/api";
import formatDateTime from "../utils/formatDateTime";
import BroadcastMode from "../components/BroadcastMode";

const Home = () => {

  const [dashboardSummary, setDashboardSummary] = useState({})

  useEffect(() => {

    const getSummary = async () => {

      const { data: { data: summaryData } } = await api.get("/dashboard/summary");

      setDashboardSummary(summaryData);
    }
    getSummary();

  }, [])

  /* 
  const [weather, setWeather] = useState({
    temp: "28°C",
    condition: "Sunny",
    icon: "sun",
  });


  const getWeatherIcon = () => {
    switch (weather.icon) {
      case "cloud":
        return <CloudIcon className="w-6 h-6 text-blue-500" />;
      case "rain":
        return <CloudRainIcon className="w-6 h-6 text-blue-500" />;
      default:
        return <SunIcon className="w-6 h-6 text-yellow-500" />;
    }
  };

  */

  const lastBroadcast = dashboardSummary?.recentBroadcasts?.[0];


  return (
    <div className="flex-1 bg-gray-100 p-5 h-full overflow-y-auto">
      <Greeting />
      <h1 className="text-2xl font-bold mb-6 text-center">Dashboard</h1>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
        <div className="p-6 rounded-2xl shadow flex items-center gap-4 bg-gray-200 hover:bg-gray-300">
          <div className="p-3 rounded-xl bg-blue-50 text-blue-600">
            <UserGroupIcon className="w-7 h-7" />
          </div>
          <div>
            <p className="text-gray-500 text-sm">Total Residents</p>
            <p className="text-3xl font-bold">{dashboardSummary?.residentsCount || "-"}</p>
          </div>
        </div>

        <div className="p-6 rounded-2xl shadow flex items-center gap-4 bg-gray-200 hover:bg-gray-300">
          <div className="p-3 rounded-xl bg-blue-50 text-blue-600">
            <BellIcon className="w-7 h-7" />
          </div>
          <div>
            <p className="text-gray-500 text-sm">Alerts this month</p>
            <p className="text-3xl font-bold">{dashboardSummary?.broadcastCountThisMonth || "-"}</p>
          </div>
        </div>

        <div className="p-6 rounded-2xl shadow flex items-center gap-4 bg-gray-200 hover:bg-gray-300">
          <div className="p-3 rounded-xl bg-blue-50 text-blue-600">
            <MegaphoneIcon className="w-7 h-7" />
          </div>
          <div>
            <p className="text-gray-500 text-sm">Last Alert</p>
            {lastBroadcast && (

              <p className="text-xl font-bold">
                {`${lastBroadcast.title} - ${formatDateTime(lastBroadcast.broadcastDateTime)}`}

              </p>

            )}

          </div>
        </div>

        {/*
        <div className="p-6 rounded-2xl shadow flex items-center gap-4 bg-gray-200 hover:bg-gray-300">
          <div className="p-3 rounded-xl bg-blue-50 text-blue-600">
            {getWeatherIcon()}
          </div>
          <div>
            <p className="text-gray-500 text-sm">Weather</p>
            <p className="text-xl font-bold">
              {" "}
              {weather.temp} • {weather.condition}
            </p>
          </div>
        </div>

        <div className="p-6 rounded-2xl shadow bg-gray-200 hover:bg-gray-300">
          <h3 className="text-gray-500 text-sm mb-4">System Status</h3>
          <div className="space-y-3">
            <div className="flex items-center justify-between">
              <div className="flex items-center gap-3">
                <div className="p-2 rounded-lg bg-green-50 text-green-600">
                  <PhoneIcon className="w-5 h-5" />
                </div>
                <span className="text-gray-700 font-medium">Voice</span>
              </div>
              <span className="text-sm font-semibold text-green-600">
                Operational
              </span>
            </div>

            <div className="flex items-center justify-between">
              <div className="flex items-center gap-3">
                <div className="p-2 rounded-lg bg-green-50 text-green-600">
                  <ChatBubbleLeftRightIcon className="w-5 h-5" />
                </div>
                <span className="text-gray-700 font-medium">Text Message</span>
              </div>
              <span className="text-sm font-semibold text-green-600">
                Operational
              </span>
            </div>
          </div>
        </div>
        */}
      </div>

      <div className="bg-gray-200 p-6 rounded-2xl shadow">
        <h2 className="text-lg font-semibold mb-4">Recent Broadcasts</h2>
        <div className="overflow-x-auto">
          <table className="min-w-full bg-white shadow rounded-lg overflow-hidden">
            <thead className="bg-blue-900 text-center text-white">
              <tr>
                <th className="px-4 py-3">Title</th>
                <th className="px-4 py-3">Mode</th>
                <th className="px-4 py-3">Date</th>
                <th className="px-4 py-3">Status</th>
              </tr>
            </thead>
            <tbody className="text-center divide-y divide-gray-200">
              {dashboardSummary?.recentBroadcasts?.length > 0 ? (
                dashboardSummary?.recentBroadcasts?.map((broadcast) => (
                  <tr
                    key={broadcast.id}
                    className="hover:bg-gray-100 text-base"
                  >
                    <td className="px-4 py-3">{broadcast.title}</td>
                    <td className="px-4 py-3">{<BroadcastMode mode={broadcast.broadcastMode} />}</td>
                    <td className="px-4 py-3">{formatDateTime(broadcast.broadcastDateTime)}</td>
                    <td className="px-4 py-3">
                      <span
                        className={`px-3 py-1 rounded-full font-medium ${broadcast.status === "Completed"
                          ? "bg-green-100 text-green-700"
                          : "bg-red-100 text-red-700"
                          }`}
                      >
                        {broadcast.status}
                      </span>
                    </td>
                  </tr>
                ))) : (
                <tr>
                  <td colSpan={4} className="py-4 text-red-500 font-medium">
                    No broadcasts available
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default Home;
