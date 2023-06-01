import React from "react";

function ShowRate({ rate }) {
    const fullStar = Math.floor(rate);
    const halfStar = rate % 1 >= 0.5 ? 1 : 0; //소수점 내림으로 계산. ex) 4.65 => 별 4.5개
    const emptyStar = 5 - fullStar - halfStar;

    function RenderStar({ type }) {
        const starType = {
            full: ["star-left-full", "star-right-full"],
            half: ["star-left-full", "star-right-empty"],
            empty: ["star-left-empty", "star-right-empty"],
        };

        return (
            <React.Fragment>
                <button className={starType[type][0]} />
                <button className={starType[type][1]} />
            </React.Fragment>
        );
    }

    return (
        <div>
            {[...Array(fullStar)].map((_, index) => (
                <RenderStar key={`fullStar${index}`} type="full" />
            ))}
            {[...Array(halfStar)].map((_, index) => (
                <RenderStar key={`halfStar${index}`} type="half" />
            ))}
            {[...Array(emptyStar)].map((_, index) => (
                <RenderStar key={`emptyStar${index}`} type="empty" />
            ))}
        </div>
    );
}

export default ShowRate;
