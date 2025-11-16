const Pagination = ({ pageable, handlePagination, source }) => {
  
  if(!pageable || Object.keys(pageable).length === 0) return;

  const pageSize = 10;

  return (
    <div className="flex justify-between items-center p-4 bg-gray-50 border-t">
      <div className="text-gray-600">
        {`Showing ${pageable.pageNumber * pageable.pageSize + 1} to ${Math.min(
          (pageable.pageNumber + 1) * pageable.pageSize,
          pageable.totalElements
        )} of ${pageable.totalElements} ${source}`}
      </div>
      <div className="flex gap-2">
        {pageable.hasPrevious && (
          <button
            onClick={() => handlePagination(pageable.pageNumber - 1, pageSize)}
            className="px-4 py-2 rounded-lg bg-blue-600 text-white hover:bg-blue-700"
          >
            Previous
          </button>
        )}
        {pageable.hasNext && (
          <button
            onClick={() => handlePagination(pageable.pageNumber + 1, pageSize)}
            className="px-4 py-2 rounded-lg bg-blue-600 text-white hover:bg-blue-700"
          >
            Next
          </button>
        )}
      </div>
    </div>
  );
};

export default Pagination;
