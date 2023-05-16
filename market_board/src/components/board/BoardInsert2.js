import React, { useRef } from 'react'
import ReactQuill from 'react-quill'

function BoardInsert2() {
    const editor = useRef();

    function onClickHandler() {
        const formData = new FormData();
        const title = formData.get("title")
        const content = formData.get("content")
        const cid = formData.get("cid")

        const dto = {
            title,
            content,
            cid

        }
        fetch("POST", "http://localhost:8000/board-service/boards", dto)
        .then(data => {
            console.log(data.result)
        })
    }

    

    const modules = {
        toolbar: {
          container: [
            ['bold', 'italic', 'underline', 'strike'],
            ['link', 'image'],
          ],
          handlers: {
            
          },
        },
      };
  return (
    <div>
    <button onClick={onClickHandler}>Click!</button>
    <ReactQuill
      ref={editor}
      theme="snow"
      modules={modules}
    />
  </div>
  )
}

export default BoardInsert2