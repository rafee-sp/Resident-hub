import { ArrowLeftEndOnRectangleIcon } from "@heroicons/react/24/outline";
import { useAuth } from "../context/AuthContext";
import { useDispatch } from "react-redux";
import { resetMeta } from "../store/Metaslice";

const Logout = ({ isCollapsed }) => {

    const {logout} = useAuth();
    const dispatch = useDispatch();

    const handleLogout = async () => {

      await logout();

      dispatch(resetMeta())

    }

  return (
    <div className="p-4 border-t border-blue-700">
      <button className="flex items-center gap-3 w-full px-2 py-2 bg-red-600 rounded hover:bg-red-500" onClick={handleLogout}>
        <ArrowLeftEndOnRectangleIcon className="h-6 w-6" />
        {!isCollapsed && <span>Logout</span>}
      </button>
    </div>
  );
};

export default Logout;
