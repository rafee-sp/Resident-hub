import { useAuth } from "../context/AuthContext";

const Greeting = () => {

 const {user} = useAuth();

  const hour = new Date().getHours();

  const getGreeting = () => {
    if (hour >= 5 && hour < 12) return "🌅 Good Morning";
    if (hour >= 12 && hour < 17) return "☀️ Good Afternoon";
    if (hour >= 17 && hour < 20) return "🌆 Good Afternoon";
    return "🌙 Good Night";
  };

  return (
    <div className="mb-6">
      <h1 className="text-2xl font-bold">{getGreeting()}, {user}</h1>
      <p className="text-gray-600 text-sm">
        Here’s a quick look at your community today
      </p>
    </div>
  );
};

export default Greeting;
