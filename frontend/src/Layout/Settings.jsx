import { useState } from "react";
import {
  UserIcon,
  BuildingOfficeIcon,
  TagIcon,
  ArrowLeftIcon,
} from "@heroicons/react/24/outline";
import { FaRegFileAlt } from "react-icons/fa";
import Maintenance from "../components/Maintenance";
import UserManagement from "./UserManagement";

const Settings = () => {
  const [selectedTab, setSelectedTab] = useState(null);

  const cards = [
    {
      id: "users",
      label: "Users",
      description: "Manage user accounts",
      icon: <UserIcon className="w-10 h-10 text-blue-600" />,
      gradient:
        "from-blue-100 to-blue-200 hover:from-blue-200 hover:to-blue-300",
    },
    {
      id: "buildings",
      label: "Buildings",
      description: "Organize buildings",
      icon: <BuildingOfficeIcon className="w-10 h-10 text-green-600" />,
      gradient:
        "from-green-200 to-green-100 hover:from-green-300 hover:to-green-200",
    },
    {
      id: "tags",
      label: "Tags",
      description: "Group residents with tags",
      icon: <TagIcon className="w-10 h-10 text-green-600" />,
      gradient:
        "from-green-100 to-green-200 hover:from-green-200 hover:to-green-300",
    },
    {
      id: "templates",
      label: "Templates",
      description: "Quick message templates",
      icon: <FaRegFileAlt className="w-10 h-10 text-blue-600" />,
      gradient:
        "from-blue-200 to-blue-100 hover:from-blue-300 hover:to-blue-200",
    },
  ];

  return (
    <div className="p-4">
      {/* Back button */}
      {selectedTab && (
        <button
          onClick={() => setSelectedTab(null)}
          className="inline-flex items-center gap-2 px-4 py-2 rounded-lg text-white bg-blue-600 hover:bg-blue-700 transition"
        >
          <ArrowLeftIcon className="w-5 h-5" />
          <span>Back</span>
        </button>
      )}

      {/* Cards grid */}
      {selectedTab === null && (
        <div>
          <h1 className="font-bold text-3xl text-center mb-6">Settings</h1>
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-6">
            {cards.map((card) => (
              <div
                key={card.id}
                onClick={() => setSelectedTab(card.id)}
                className={`flex flex-col items-center justify-center p-8 rounded-2xl shadow-md border border-gray-200 cursor-pointer transition bg-gradient-to-br ${card.gradient}`}
              >
                <div className="mb-4">{card.icon}</div>
                <h3 className="text-lg font-semibold text-gray-800">
                  {card.label}
                </h3>
                <p className="text-sm text-gray-600 mt-1">{card.description}</p>
              </div>
            ))}
          </div>
        </div>
      )}

      {selectedTab === "users" && (
        <UserManagement />
      )}

      {["buildings", "tags", "templates"].includes(selectedTab) && (
        <Maintenance />
      )}
    </div>
  );
};

export default Settings;
