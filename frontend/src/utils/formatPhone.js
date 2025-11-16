  const formatPhone = (phoneNumber) => {
    if (!phoneNumber) return "-";
    return phoneNumber.replace(/(\d{3})(\d{3})(\d{4})/, "($1) $2-$3");
  };

export default formatPhone;