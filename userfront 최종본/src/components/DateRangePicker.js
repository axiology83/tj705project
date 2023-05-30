import React, { useState } from "react";
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

// 달력 띄워서 날짜 찍을 수 있게 해줌. npm install react-datepicker 
function DateRangePicker({ onChange }) {
    
    const [startDate, setStartDate] = useState(null);
    const [endDate, setEndDate] = useState(null);

    const handleStartDateChange = (date) => {
        setStartDate(date);
        onChange && onChange({ startDate: date, endDate });
        console.log(date)
    };

    const handleEndDateChange = (date) => {
        setEndDate(date);
        onChange && onChange({ startDate, endDate: date });
        console.log(date)
    };

    return (
        <div>
            <DatePicker
                selected={startDate}
                onChange={handleStartDateChange}
                selectsStart
                startDate={startDate}
                endDate={endDate}
                placeholderText="시작 날짜"
            />
            <DatePicker
                selected={endDate}
                onChange={handleEndDateChange}
                selectsEnd
                startDate={startDate}
                endDate={endDate}
                placeholderText="종료 날짜"
                minDate={startDate}
            />
        </div>
    );
}

export default DateRangePicker;