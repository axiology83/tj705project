import React, { useRef } from "react";
import ReactQuill from "react-quill";
import "react-quill/dist/quill.snow.css";

function ReviewInsert() {
  const editor = useRef();

  const onClickHandler = () => {
    const htmlstr = editor.current.getEditor().root.innerHTML;
    console.log(htmlstr);
  };

  const modules = {
    toolbar: {
      container: [
        [{ header: [1, 2, false] }],
        ["bold", "italic", "underline", "strike"],
        [{ align: [] }, { color: [] }, { background: [] }],
      ],
      handlers: {},
    },
  };

  return (
    <div>
      <br /> <br /> <br />
      제목
      <input style={{ fontSize: "30px" }} />
      <br />
      <button onClick={onClickHandler}>Click!</button>
      <ReactQuill
        style={{ height: "600px", width: "1000px", margin: "auto" }}
        ref={editor}
        theme="snow"
        modules={modules}
      />
    </div>
  );
}

export default ReviewInsert;
