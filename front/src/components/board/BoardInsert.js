
import React, { useEffect, useRef, useState } from 'react';
import ReactQuill, { Quill } from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import { fetchFn } from '../../NetworkUtils';

function BoardInsert() {
  const [boards, setBoards] = useState([])
  const editor = useRef();


  function onSubmitHandler(e) {
    e.preventDefault()

    const formData = new FormData(e.target)
    const username = formData.get('username')
    const title = formData.get('title')
    const content = editor.current.getEditor().root.innerHTML;
    const cid = formData.get('cid')

    const dto = {
      username,
      title,
      content,
      cid
    }
    fetchFn("POST", "http://localhost:8000/board-service/boards",dto)
    .then(data => {
      console.log(data)
    })
   }

   useEffect(() => {
    fetchFn("GET", "http://localhost:8000/category-service/category/all")
    .then(data => {
      console.log(data)
      setBoards(data)
    })
  }, [])

    /////////////////////////////////////////

    const onClickHandler = () => {
      const htmlstr = editor.current.getEditor().root.innerHTML;
      console.log(htmlstr);
    };
  
    const uploadImgCallBack = async (file) => {
      const formData = new FormData();
      formData.append('file', file);
      formData.append('filename', file.name);
  
      try {
        const response = await fetch('http://localhost:51320/imgfile-service/uploadimg', {
          method: 'POST',
          body: formData,
        });
        const data = await response.json();
        return `http://localhost:51320/imgfile-service/getimgdata?id=${data.result}`;
      } catch (error) {
        console.log(error);
      }
    };

    ///////////////////////////////////////////
  
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
       {boards.length > 0 &&
      <form action='#' onSubmit={onSubmitHandler}>
        <>
        <select id="cid" name='cid'>
        { boards.map(bc => 
          <option key={bc.id} value={bc.id}>{bc.name}</option>
          )}
        </select></>
        
        <input name='username' /><br/>
        <input placeholder='제목을 입력해주세요' name='title' style={{margin: "auto", fontSize : "18px"}}/> <br/>
    
<div>
         <button onClick={onClickHandler}>Click!</button><br/>

         
     <ReactQuill 
      placeholder='내용을 입력해주세요'
      ref={editor}
      theme="snow"
      style={{margin: "auto", height : "600px", width : "800px"}}
      modules={modules}

      
    /> 
    </div> 
     </form>
     }
  </div>
);
}
 


export default BoardInsert