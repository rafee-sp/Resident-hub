import { useEffect, useState } from "react";
import api from "../api/api";
import Pagination from "../components/Pagination";
import { DEFAULT_PAGE, DEFAULT_PAGE_SIZE } from "../utils/Pagination";
import TableSkeleton from "../components/TableSkeleton";
import ConfirmationModal from "../components/Modal/ConfirmationModal";
import AddUser from "../components/Modal/AddUser";
import ResetPassword from "../components/Modal/ResetPassword";

const UserManagement = () => {
  const [users, setUsers] = useState([]);
  const [pagination, setPagination] = useState({});
  const [selectedUser, setSelectedUser] = useState(null);
  const [loading, setLoading] = useState(false);
  const [confirmationModalOpen, setConfirmationModalOpen] = useState(false);
  const [showAddUserModal, setShowAddUserModal] = useState(false);
  const [showResetPassModal, setShowResetPassModal] = useState(false);

  useEffect(() => {
    getUsers(DEFAULT_PAGE, DEFAULT_PAGE_SIZE);
  }, []);


  const getUsers = async (pageNumber, pageSize) => {
    setLoading(true);
    try {
      const {
        data: { data: usersList, page: paginationInfo },
      } = await api.get("/users", {
        params: { page: pageNumber, size: pageSize },
      });
      setUsers(usersList);
      setPagination(paginationInfo || {});
    } catch (error) {
      console.error("Failed to fetch users", error);
      setUsers([]);
    } finally {
      setLoading(false);
    }
  };

  const handlePagination = (pageNumber, pageSize) => {
    setLoading(true);
    getUsers(pageNumber, pageSize);
  };

  const handleDeleteUser = (user) => {
    setSelectedUser(user);
    setConfirmationModalOpen(true);
  };

  const handleDelete = async () => {
    await api.delete(`/users/${selectedUser.id}`);
    setUsers(users.filter((user) => user.id !== selectedUser.id));
    setSelectedUser(null);
    setConfirmationModalOpen(false);
  };

  const handleAddUser = () => {
    setShowAddUserModal(true);
  };

  const handleResetPassword = (user) => {
    setSelectedUser(user);
    setShowResetPassModal(true);
  };

  return (
    <div className="p-2">
      <div className="flex items-center justify-center gap-3 mb-2">
        <h1 className="text-2xl font-bold text-gray-800">User Management</h1>
      </div>
      <div className="flex justify-end gap-5 mb-3">
        <button
          className="text-white p-3 bg-blue-600 hover:bg-blue-700 rounded-xl"
          onClick={handleAddUser}
        >
          Add User
        </button>
      </div>

      <div className="bg-white rounded-lg shadow overflow-x-auto">
        <table className="min-w-full bg-white shadow rounded-lg overflow-hidden">
          <thead className="bg-blue-900 text-center text-white">
            <tr>
              <th className="px-4 py-3">User Name</th>
              <th className="px-4 py-3">Email</th>
              <th className="px-4 py-3">Reset password</th>
              <th className="px-4 py-3">Remove</th>
            </tr>
          </thead>
          {loading ? (
            <TableSkeleton columns={4} rows={10} />
          ) : (
            <tbody className="text-center divide-y divide-gray-200">
              {users && users.length > 0 ? (
                users.map((user) => (
                  <tr key={user.id} className="hover:bg-gray-100 text-semibold">
                    <td className="px-4 py-3 max-w-xs"> {user.username}</td>
                    <td className="px-4 py-2">{user.email}</td>
                    <td className="px-4 py-3">
                      <button
                        className="text-white px-3 py-2 bg-green-600 hover:bg-green-700 rounded-xl"
                        onClick={() => handleResetPassword(user)}
                        disabled={loading}
                      >
                        Reset
                      </button>
                    </td>
                    <td className="px-4 py-3 text-base text-gray-800">
                      <button
                        className="text-white px-3 py-2 bg-red-600 hover:bg-red-700 rounded-xl"
                        disabled={loading}
                        onClick={() => handleDeleteUser(user)}
                      >
                        Remove
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
                    No Users found
                  </td>
                </tr>
              )}
            </tbody>
          )}
        </table>
        {!loading && (
          <Pagination
            handlePagination={handlePagination}
            source={"Users"}
            pageable={pagination}
          />
        )}
      </div>

      {confirmationModalOpen && (
        <ConfirmationModal
          content={
            <>
              Are you sure you want to delete the User{" "}
              <strong>{selectedUser?.username}</strong> ?
            </>
          }
          handleClose={() => setConfirmationModalOpen(false)}
          onConfirm={handleDelete}
          title={"Delete User"}
        />
      )}

      {showAddUserModal && (
        <AddUser
          onClose={() => setShowAddUserModal(false)}
          onSuccess={() => getUsers(DEFAULT_PAGE, DEFAULT_PAGE_SIZE)}
        />
      )}

      {selectedUser && showResetPassModal && (
        <ResetPassword
          onClose={() => setShowResetPassModal(false)}
          selectedUserId={selectedUser.id}
        />
      )}
    </div>
  );
};

export default UserManagement;
