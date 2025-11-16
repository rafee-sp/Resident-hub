import { useState, useEffect } from "react";
import ResidentModal from "./Modal/ResidentFormModal";

const ResidentToolBar = ({ searchResident, onAdd }) => {
  // search state
  const [searchType, setSearchType] = useState("RESIDENT_NAME");
  const [searchValue, setSearchValue] = useState("");

  const getInputValidation = () => {
    if (searchType === "RESIDENT_NAME") {
      return {
        type: "text",
        placeholder: "Enter Resident Name",
        maxLength: 45,
        onInput: (e) => {
          e.target.value = e.target.value.replace(/[^a-zA-Z\s]/g, "");
        },
      };
    } else if (searchType === "RESIDENT_ID") {
      return {
        type: "text",
        placeholder: "Enter Resident Id",
        maxLength: 10,
        onInput: (e) => {
          e.target.value = e.target.value.replace(/[^0-9]/g, "");
        },
      };
    } else if (searchType === "PHONE_NUMBER") {
      return {
        type: "text",
        placeholder: "Enter Phone Number",
        maxLength: 10,
        onInput: (e) => {
          e.target.value = e.target.value.replace(/[^0-9]/g, "");
        },
      };
    } else if (searchType === "BUILDING") {
      return {
        type: "text",
        placeholder: "Enter Building Name",
        maxLength: 10,
      };
    } else if (searchType === "FLAT_NO") {
      return {
        type: "text",
        placeholder: "Enter Flat Number",
        maxLength: 10,
      };
    } else {
      return {
        type: "text",
        placeholder: "Enter Search Value",
        maxLength: 45,
      };
    }
  };

  useEffect(() => {

    if (!searchValue.trim()) return;
    console.log("Searching for:", searchType, searchValue.trim());
    searchResident(searchType, searchValue.trim());

  }, [searchType, searchValue, searchResident]);

  return (
    <div className="flex items-center justify-between mb-4">
      <div className="flex gap-3 p-3 rounded-lg">
        <select
          value={searchType}
          onChange={(e) => {
            setSearchType(e.target.value);
            setSearchValue("");
          }}
          className="border bg-white border-gray-300 rounded-lg px-4 py-3 text-gray-700 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none"
        >
          <option value="RESIDENT_NAME">Resident Name</option>
          <option value="RESIDENT_ID">Resident Id</option>
          <option value="PHONE_NUMBER">Phone number</option>
          <option value="BUILDING">Building Name</option>
          <option value="FLAT_NO">Flat Number</option>
        </select>

        <div className="relative flex-1">
          <input
            {...getInputValidation()}
            value={searchValue}
            onChange={(e) => setSearchValue(e.target.value)}
            className=" border bg-white border-gray-300 rounded-lg px-4 py-3 pr-10 text-gray-700 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none"
          />
        </div>
      </div>
      <div className="text-gray-600 text-xl font-bold">
        <button
          onClick={onAdd}
          className="p-2 rounded-lg text-white bg-blue-600 hover:bg-blue-700 shadow-md transition"
        >
          Add Resident
        </button>
      </div>
    </div>
  );
};

export default ResidentToolBar;
