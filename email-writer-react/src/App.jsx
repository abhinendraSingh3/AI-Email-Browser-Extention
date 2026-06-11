import { useState } from 'react'
import './App.css'
import { Button } from '@mui/material';
import axios from 'axios';

function App() {
  const [emailContent, setEmailContent] = useState('');
  const [tone, setTone] = useState("");
  const [generateReply,setGenerateReply]=useState("")
  const[error,setError]=useState("")
  const [loading,setLoading]=useState("")

  const handleSubmit=async()=>{
    setLoading(true);
    setError('');

    try{
      const response =await axios.post("http://localhost:8080/api/email/generate",{emailContent,tone});
       setGenerateReply(
        typeof response.data=='string' ? response.data : JSON.stringify(response.data)
       );
    }
    catch(error){
      console.error(error);
      setError("Failed to generate reply. Please try again");

    }
    finally{
      setLoading(false);
  }

    

  }

  return (
    <>
      <h1>Email Reply Generator</h1>
      <textarea value={emailContent} placeholder='Original Email Content' onChange={(e)=>{setEmailContent(e.target.value); setError("")}}></textarea><br />


      <label>Tone (Optional)</label><br/>
      <select 
      value={tone}
      onChange={(e)=>setTone(e.target.value)}
      style={{
            width: "20vw",
            padding: "10px",
            border: "1px solid #ccc",
          }}
      >
        <option value="">Select Tone</option>
        <option value="professional">Professional</option>
        <option value="Friendly">Friendly</option>
        <option value="Casual">Casual</option>
        <option value="Formal">Formal</option>
      </select><br/>

      <button 
      type='button' 
      onClick={handleSubmit} disabled={loading || emailContent.trim()===""}>{loading?"Generating...":"Generate Reply"}</button>

      {
        error ?<p>{error}</p>: null
      }

      {
      generateReply &&(
        <>
        <h2>Generated Reply</h2>

        <textarea
        value={generateReply}
        readOnly
        /><br/>
        <button
        onClick={()=>navigator.clipboard.writeText(generateReply)}>
          Copy Reply
        </button>

        </>
      )}

    </>
  )
}

export default App
