const TableSkeleton = ({ rows = 10, columns = 5 }) => {
  return (
    <tbody>
      {Array.from({ length: rows }).map((_, rowIndex) => (
        <tr key={rowIndex} className="animate-pulse">
          {Array.from({ length: columns }).map((_, colIndex) => (
            <td key={colIndex} className="px-4 py-3 ">
              <div className="h-6 bg-gray-200 rounded w-3/4"></div>
            </td>
          ))}
        </tr>
      ))}
    </tbody>
  );
};

export default TableSkeleton;
