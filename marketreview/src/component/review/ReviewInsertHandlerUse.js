import React, { useRef, useState } from "react";
import ReactQuill from "react-quill";
import "react-quill/dist/quill.snow.css";
import { fetchFn } from "../../NetworkUtils";

function ReviewInsert() {
  const editor = useRef();
  const [content, setcontent] = useState();

  const onClickHandler = () => {
    const htmlstr = editor.current.getEditor().root.innerHTML;
    console.log(htmlstr);
    setcontent(htmlstr);
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

  function onSubmitHandler(e) {
    e.preventDefault();
    const formData = new FormData(e.target);
    const htmlstr = editor.current.getEditor().root.innerHTML;
    console.log(htmlstr);
    const dto = {
      sellerId: "파는사람",
      buyerName: "사는사람",
      title: formData.get("title"),
      content: content,
      rate: formData.get("rate"),
    };
    fetchFn("POST", `http://localhost:8000/review-service/create`, dto).then(
      (data) => {
        console.log(data);
      }
    );
  }

  return (
    <div>
      <br /> <br /> <br />
      제목
      <form action="#" onSubmit={onSubmitHandler}>
        <input style={{ fontSize: "30px" }} name="title" />
        <br />
        별점 : <input style={{ fontSize: "30px" }} name="rate" />
        <br />
        <button>작성 완료</button>
      </form>
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
