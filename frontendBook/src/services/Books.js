import axios from "axios";

let URL_API = "http://localhost:8080/api/libros/"

const onInsert = async (author, name, gener, age) => {
    try {
        const response = await axios.post(URL_API, {
            author: author,
            nameBook: name,
            gener: gener,
            agePubli: age,
        });
        console.log(response.data);
        
    } catch (error) {
        throw(error)
    }
}

const onGetAll = async () => {
    try {
        const response = await axios.get(URL_API);
        return response.data.data;
    } catch (error) {
        throw(error)
    }
}

const onDelete = async (idBook) => {
    try {
        const response = await axios.delete(`${URL_API}${idBook}`);
        return response.data;
    } catch (error) {
        throw(error)
    }
}

const onUpdate = async ( idBook, author, name, gener, age ) => {
    try {
        const response = await axios.put(URL_API, {
            id: idBook,
            author: author,
            nameBook: name,
            gener: gener,
            agePubli: age,
        });
        console.log(response.data);
        
    } catch (error) {
        throw(error)
    }
}

export default {
    onInsert,
    onGetAll,
    onUpdate,
    onDelete
}