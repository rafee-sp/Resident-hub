
const formatDateTime = (dateTimeStr) => {

    const date = new Date(dateTimeStr.replace(" ", "T"));


    // Feb 25, 2024 11:30 AM
    const formattedDate = date.toLocaleString("en-US", {

        month: "short",
        day: "2-digit",
        year: "numeric",
    })

    const formattedTime = date.toLocaleTimeString("en-US", {
        hour: "numeric",
        minute: "2-digit",
        hour12: true,
    })

    return `${formattedDate} ${formattedTime}`;

}

export default formatDateTime;