import { useState } from "react";
import Residents from "../Layout/Residents";
import Home from "../Layout/Home";
import BroadcastHistory from "../Layout/BroadcastHistory";
import Settings from "../Layout/Settings";
import Navbar from "../Layout/Navbar";
import Broadcast from "../Layout/Broadcast";
import Activity from "../Layout/Activitiy";

const Dashboard = () => {

  const [currentTab, setCurrentTab] = useState("home");

  return (
    <div className="bg-gray-200 flex h-screen">
      <Navbar currentTab={currentTab} setCurrentTab={setCurrentTab} />
      <div className="flex-1 p-6 overflow-y-auto">      
          {currentTab === "home" && <Home />} 
          {currentTab === "residents" && <Residents />}
          {currentTab === "broadcast" && <Broadcast />}
          {currentTab === "history" && <BroadcastHistory />} 
          {currentTab === "activity" && <Activity />} 
          {currentTab === "setting" && <Settings />}       
      </div>      
    </div>
  );
};

export default Dashboard;
