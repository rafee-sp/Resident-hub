const Maintenance = () => {
  return (
     <div className="flex flex-col items-center justify-center h-full w-full bg-inherit">
      <img
        src="/images/maintenance.png"
        alt="Under Maintenance"
        className="w-96 h-96 object-contain mb-8"
      />
      <h3 className="text-3xl font-bold text-gray-800">Under Maintenance</h3>
      <p className="text-lg text-gray-600 mt-4 text-center max-w-md">
        This section is currently being worked on. Please check back later.
      </p>
    </div>
  );
}

export default Maintenance;
