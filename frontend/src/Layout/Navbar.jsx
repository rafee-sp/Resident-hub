import { useEffect, useState } from "react";
import {
  HomeIcon,
  UserGroupIcon,
  MegaphoneIcon,
  ClockIcon,
  Cog6ToothIcon,
  Bars3Icon,
  ListBulletIcon
} from "@heroicons/react/24/outline";
import Logout from "../components/Logout";

const menuItems = [
  { id: "home", name: "Home", icon: HomeIcon },
  { id: "residents", name: "Residents", icon: UserGroupIcon },
  { id: "broadcast", name: "Broadcast", icon: MegaphoneIcon, },
  { id: "history", name: "Broadcast History", icon: ClockIcon },
  { id: "activity", name: "Activity", icon: ListBulletIcon },
  { id: "setting", name: "Settings", icon: Cog6ToothIcon },
];

const Navbar = ({ currentTab, setCurrentTab }) => {
  const [isCollapsed, setIsCollapsed] = useState(false);

  useEffect(() => {
    const saved = localStorage.getItem("sidebarCollapsed");
    if (saved) setIsCollapsed(JSON.parse(saved));
  }, []);

  useEffect(() => {
    localStorage.setItem("sidebarCollapsed", JSON.stringify(isCollapsed));
  }, [isCollapsed]);

  return (
    <aside
      className={`${isCollapsed ? "w-20" : "w-64"
        } bg-blue-900 text-white flex flex-col transition-all duration-300 `}
    >
      <div className="flex items-center justify-between p-4">
        {!isCollapsed && (
          <>
            <img src="/images/ResidentHub-logo.png" alt="Logo" className="h-8 w-auto" />
            <h1 className="text-xl font-bold">ResidentHub</h1>
          </>)}
        <button
          onClick={() => setIsCollapsed(!isCollapsed)}
          className="p-2 rounded hover:bg-blue-700"
        >
          <Bars3Icon className="h-6 w-6" />
        </button>
      </div>

      <nav className="flex-1 p-4 space-y-2">
        {menuItems.map(({ id, name, icon: Icon }) => (
          <button
            key={id}
            onClick={() => setCurrentTab(id)}
            className={`flex items-center gap-3 w-full text-left px-3 py-2 rounded transition-colors ${currentTab === id ? "bg-blue-700 font-semibold border-l-4 border-blue-300" : "hover:bg-blue-700"}
              }`}
          >
            <Icon className="h-6 w-6" />
            {!isCollapsed && <span>{name}</span>}
          </button>
        ))}
      </nav>

      <Logout isCollapsed={isCollapsed} />

    </aside>
  );
};

export default Navbar;
