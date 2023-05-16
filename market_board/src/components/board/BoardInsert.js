
import React, { useRef, useState } from 'react';
import ReactQuill, { Quill } from 'react-quill';
import 'react-quill/dist/quill.snow.css';

function BoardInsert() {
   


    const editor = useRef();

    const onClickHandler = () => {
      const htmlstr = editor.current.getEditor().root.innerHTML;
      console.log(htmlstr);
    };
  
    const uploadImgCallBack = async (file) => {
      const formData = new FormData();
      formData.append('file', file);
      formData.append('filename', file.name);
  
      try {
        const response = await fetch('http://localhost:9007/imgService/uploadImg', {
          method: 'POST',
          body: formData,
        });
        const data = await response.json();
        return `http://localhost:9007/imgService/getImg?id=${data.result.id}`;
      } catch (error) {
        console.log(error);
      }
    };
  
    const imageHandler = () => {
      const quillEditor = editor.current.getEditor();
      const input = document.createElement('input');
      input.setAttribute('type', 'file');
      input.setAttribute('accept', 'image/*');
      input.click();
  
      input.onchange = async () => {
        const file = input.files[0];
        const url = await uploadImgCallBack(file);
        const range = quillEditor.getSelection(true);
        quillEditor.insertEmbed(range.index, 'image', url);
      };
    };
  
    const modules = {
      toolbar: {
        container: [
          ['bold', 'italic', 'underline', 'strike'],
          ['link', 'image'],
        ],
        handlers: {
          image: imageHandler,
        },
      },
    };



  return (
    <div>
        <input style={{margin: "auto", fontSize : "18px"}}/> <br/>
    <button onClick={onClickHandler}>Click!</button><br/>
    <ReactQuill
      ref={editor}
      theme="snow"
      style={{margin: "auto", height : "600px", width : "800px"}}
      modules={modules}

      
    />
  </div>
);
}
 


export default BoardInsert