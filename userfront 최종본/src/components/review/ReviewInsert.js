import React, { useState } from "react";
import "react-quill/dist/quill.snow.css";
import GetRate from "./GetRate";
import Quill from "./Quill";
import { fetchFn } from "../NetworkUtils";

function ReviewInsert() {
  const [qeditor, setQeditor] = useState();
  const [score, setScore] = useState();

  const uploadImgCallBack = async (file) => {
    const formData = new FormData();
    formData.append("file", file);
    formData.append("filename", file.name);

    try {
      const response = await fetch(
        "http://localhost:8000/imgfile-service/uploadimg",
        {
          method: "POST",
          body: formData,
        }
      );
      const data = await response.json();
      return `http://localhost:8000/imgfile-service/getimgdata?id=${data.result}`;
    } catch (error) {
      console.log(error);
    }
  };

  const imageHandler = () => {
    const quillEditor = qeditor.current.getEditor();
    const input = document.createElement("input");
    input.setAttribute("type", "file");
    input.setAttribute("accept", "image/*");
    input.click();

    input.onchange = async () => {
      const file = input.files[0];
      const url = await uploadImgCallBack(file);
      const range = quillEditor.getSelection(true);
      quillEditor.insertEmbed(range.index, "image", url);
    };
  };

  function onClick(e) {
    e.preventDefault();

    const formData = new FormData(e.target);
    const title = formData.get("title");
    const content = qeditor.current.getEditor().root.innerHTML;
    const dto = {
      sellerId: "test",
      buyerName: "buyer1",
      title,
      content,
      rate: score,
    };
    fetchFn("POST", "http://localhost:8000/review-service/create", dto).then(
      (data) => {
        console.log(data);
      }
    );
  }

  return (
    <div>
      <GetRate parentStateFunction={setScore} />
      <form action="#" onSubmit={onClick}>
        <br /> <br /> <br />
        제목
        <input
          style={{ fontSize: "30px" }}
          name="title"
          placeholder="제목을 입력하세요"
        />
        <br />
        <button>작성완료!</button>
        <Quill fn={setQeditor} />
      </form>
    </div>
  );
}

export default ReviewInsert;
