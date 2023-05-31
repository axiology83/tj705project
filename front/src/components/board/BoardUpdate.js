import React, { useEffect, useRef, useState } from 'react'
import { useParams } from 'react-router-dom'
import { fetchFn } from '../../NetworkUtils';
import ReactQuill from 'react-quill';


function BoardUpdate() {
    const id = useParams().id;
    const [board, setBoard] = useState(null)
    const [boards, setBoards] = useState([])
    const editor = useRef();

    

    useEffect(() => {
        fetchFn("GET", `http://localhost:8000/board-service/board/${id}`, null)
        .then(data => {
            console.log(data)
            setBoard(data)
        })
    }, [id])

    function onSubmitHandler(e) {
        e.preventDefault();

        const formData = new FormData(e.target)
        const username = formData.get('username')
        const title = formData.get('title')
        const content = editor.current.getEditor().root.innerHTML;
        const cid = formData.get('cid')
        
        const dto ={id ,username, title, content, cid}
        console.log(dto)
    fetchFn("PUT", "http://localhost:8000/board-service/update", dto)
    .then(data => {
       console.log(data)
    /*    window.location.href =`/board/detail/${data.result.id}` */
    
     })
    }
   
    function onInputHandler(e) {
        let val = e.target.value;

        let newBoard = {...board, [e.target.name]:val};
        setBoard(newBoard)
    }

    useEffect(() => {
        fetchFn("GET", "http://localhost:8000/category-service/category/all", null)
        .then(data => {
            setBoards(data)
       } )
    }, [])


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


        
       
        {
            board !== null && <>
       
        <form action='#' onSubmit={onSubmitHandler}>
            { boards.length>0 && <>
     말머리 : <></>
        <select id="cid" name='cid'>
         { boards.map(bc => 
          <option key={bc.id} value={bc.id}>{bc.name}</option>
          )}
    </select><br/>
</>}
        글 번호 : <input value={id} readOnly/><br/>
        USERNAME : <input name='username' value={board.username} readOnly/> <br/>
        TITLE : <input name='title' value={board.title} onInput={onInputHandler}/> <br/>

        <ReactQuill
        name='content' value={board.content} onInput={onInputHandler} 
      placeholder='내용을 입력해주세요'
      ref={editor}
      theme="snow"
      style={{margin: "auto", height : "600px", width : "800px"}}
      modules={modules}
            
      
    /> 
        <button>수정하기</button>
        </form>
         </>}
    </div>
  )
}

export default BoardUpdate