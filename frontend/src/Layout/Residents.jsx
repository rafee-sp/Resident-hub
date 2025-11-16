import { useCallback, useEffect, useState } from "react";
import ResidentToolBar from "../components/ResidentToolBar";
import ResidentTable from "../components/ResidentTable";
import api from "../api/api";
import ResidentFormModal from "../components/Modal/ResidentFormModal";
import { useDispatch, useSelector } from "react-redux";
import { fetchMetaData } from "../store/Metaslice";
import ConfirmationModal from "../components/Modal/ConfirmationModal";
import MessageModal from "../components/Modal/MessageModal";
import { DEFAULT_PAGE, DEFAULT_PAGE_SIZE } from "../utils/Pagination";


const Residents = () => {
  const { metaData } = useSelector((state) => state.meta);

  const [residents, setResidents] = useState([]);
  const [pagination, setPagination] = useState({});
  const [loading, setLoading] = useState(false);
  const [searchType, setSearchType] = useState("");
  const [searchValue, setSearchValue] = useState("");

  // resident modal state
  const [residentModalOpen, setResidentModalOpen] = useState(false);
  const [messageModalOpen, setMessageModalOpen] = useState(false);
  const [confirmationModalOpen, setConfirmationModalOpen] = useState(false);
  const [selectedResident, setSelectedResident] = useState(null);
  const [selectedMessageType, setSelectedMessageType] = useState(null);

  const searchResident = useCallback(async (searchType, searchValue, pageNumber = DEFAULT_PAGE, pageSize = DEFAULT_PAGE_SIZE) => {

    if (searchValue.trim()) {
      setLoading(true);
      setSearchValue(searchValue);
      setSearchType(searchType);
      const {
        data: { data: residentsList, page: paginationInfo },
      } = await api.get("/residents/search", {
        params: {
          filterType: searchType,
          filterValue: searchValue,
          page: pageNumber,
          size: pageSize,
        },
      });

      setResidents(residentsList);
      setPagination(paginationInfo || {});
      setLoading(false);
    } else {
      setSearchValue("");
      setSearchType("");
      getResident(DEFAULT_PAGE, DEFAULT_PAGE_SIZE);
    }
  }, []);

  const dispatch = useDispatch();

  useEffect(() => {
    if (!metaData || Object.keys(metaData).length === 0) {
      dispatch(fetchMetaData());
    }
  }, [dispatch, metaData]);

  useEffect(() => {
    getResident(DEFAULT_PAGE, DEFAULT_PAGE_SIZE);
  }, []);

  const getResident = useCallback(async (pageNumber, pageSize) => {
    setLoading(true);
    const {
      data: { data: residentsList, page: paginationInfo },
    } = await api.get(`/residents?page=${pageNumber}&size=${pageSize}`);
    setResidents(residentsList);
    setPagination(paginationInfo || {});
    setLoading(false);
  }, []);

  const handlePagination = (pageNumber, pageSize) => {
    if (searchValue.trim()) {
      searchResident(searchType, searchValue, pageNumber, pageSize);
    } else {
      getResident(pageNumber, pageSize);
    }
  };

  const handleResidentModalOpen = (resident = null) => {
    console.log("called residentoPn : ", resident);
    setSelectedResident(resident);
    setResidentModalOpen(true);
  };

  const handleResidentDeleteModalOpen = (resident) => {
    console.log("called residentoPn : ", resident);
    setSelectedResident(resident);
    setConfirmationModalOpen(true);
  };

  const handleResidentSuccess = (updatedResident = null) => {
    setResidentModalOpen(false);
    setSelectedResident(null);
    if (updatedResident) {
      setResidents(
        (prev) =>
          prev.some((r) => r.id === updatedResident.id)
            ? prev.map((r) =>
              r.id === updatedResident.id ? updatedResident : r
            ) // update
            : [updatedResident, ...prev] // add new
      );
    } else {
      getResident(pagination.pageNumber || DEFAULT_PAGE, pagination.pageSize || DEFAULT_PAGE_SIZE);
    }
  };

  const handleDelete = async () => {

    if (!selectedResident?.id) return;

    await api.delete(`/residents/${selectedResident.id}`);
    setResidents((prev) =>
      prev.filter((resident) => resident.id != selectedResident.id)
    );
    setConfirmationModalOpen(false);
  };

  const handleMessageModalOpen = (messageType, resident) => {
    setSelectedMessageType(messageType)
    setSelectedResident(resident)
    setMessageModalOpen(true);
  }

  const handleMessageSuccess = () => {
    setSelectedMessageType(null)
    setSelectedResident(null)
    setMessageModalOpen(false);
  };

  return (
    <div>
      <h1 className="text-2xl font-bold mb-6 text-center">RESIDENTS</h1>
      <ResidentToolBar
        searchResident={searchResident}
        onAdd={() => handleResidentModalOpen(null)}
      />
      <ResidentTable
        residents={residents}
        pageable={pagination}
        handlePagination={handlePagination}
        isLoading={loading}
        onUpdate={(res) => handleResidentModalOpen(res)}
        onDelete={handleResidentDeleteModalOpen}
        onSendMessage={handleMessageModalOpen}
      />

      {residentModalOpen && (
        <ResidentFormModal
          onSuccess={handleResidentSuccess}
          handleClose={() => setResidentModalOpen(false)}
          resident={selectedResident}
        />
      )}

      {messageModalOpen && (
        <MessageModal
          onSuccess={handleMessageSuccess}
          onClose={() => setMessageModalOpen(false)}
          messageType={selectedMessageType}
          resident={selectedResident}
        />
      )}


      {confirmationModalOpen && selectedResident && (
        <ConfirmationModal
          content={
            <>
              Are you sure you want to delete the resident{" "}
              <strong>{selectedResident.residentName}</strong> ?
            </>
          }
          handleClose={() => setConfirmationModalOpen(false)}
          onConfirm={handleDelete}
          title={"Delete Resident"}
        />
      )}
    </div>
  );
};

export default Residents;
