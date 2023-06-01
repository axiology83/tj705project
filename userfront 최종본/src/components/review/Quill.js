import React, { useRef } from "react";
import ReactQuill from "react-quill";

function Quill({ fn }) {
  const editor = useRef();
  fn(editor);
  const onClickHandler = () => {
    // html전환
    const htmlstr = editor.current.getEditor().root.innerHTML;
    console.log(htmlstr);
  };

  const onClickHandler2 = () => {
    //델타 전환
    const htmlstr = editor.current.getEditor().getContents();
    console.log(htmlstr);
  };

  const modules = {
    toolbar: {
      container: [
        [{ header: [1, 2, false] }],
        ["bold", "italic", "underline", "strike"],
        ["link", "image"],
        [{ align: [] }, { color: [] }, { background: [] }],
      ],
      handlers: {},
    },
  };
  return (
    <div>
      <button onClick={onClickHandler}>Click!</button>
      <button onClick={onClickHandler2}>Click!</button>
      <ReactQuill
        placeholder="durldp"
        style={{ height: "400px", width: "500px", margin: "auto" }}
        ref={editor}
        theme="snow"
        modules={modules}
      />
    </div>
  );
}

export default Quill;
