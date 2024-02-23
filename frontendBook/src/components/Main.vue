<template lang="">
  <b-container fluid>
    <b-row class="p-5 text-end">
      <b-col>
        <b-button variant="primary" v-b-modal.modal-1>Agregar</b-button>
      </b-col>
    </b-row>
    <b-row>
      <b-col v-if="cargando" class="d-flex justify-content-center mb-3">
        <b-spinner style="width: 3rem; height: 3rem"></b-spinner>
      </b-col>
      <b-col cols="3" v-else v-for="libro in books" :key="libro.id">
        <b-card
          :title="libro.author"
          img-src=""
          tag="article"
          style="max-width: 20rem"
          class="mb-2"
          @click="updateBooks(libro)"
        >
          <b-card-text>
            {{ libro.nameBook }}
          </b-card-text>
          <b-card-text>
            {{ libro.gener }}
          </b-card-text>
          <b-card-text>
            {{ libro.age }}
          </b-card-text>
          <div class="text-end">
            <b-button variant="danger" @click="deleteBooks(libro.id)"
              >Eliminar</b-button
            >
          </div>
        </b-card>
      </b-col>
    </b-row>

     <b-modal 
            id="modal-1" 
            :title="modalTitle"
            scrollable
            centered
            @ok="insertBook"
        >
            <form ref="form">
                <div class="mb-3">
                    <label for="author" class="form-label">Autor: </label>
                    <input type="text" id="author" v-model="author" class="form-control">
                </div>
                <div class="mb-3">
                    <label for="nameBook" class="form-label">Nombre del Libro:</label>
                    <input type="text" id="nameBook" v-model="nameBook" class="form-control">
                </div>
                <div class="mb-3">
                    <label for="gener" class="form-label">Género:</label>
                    <input type="text" id="gener" v-model="gener" class="form-control">
                </div>
                <div class="mb-3">
                    <label for="age" class="form-label">Año de Publicación:</label>
                    <input type="number" id="age" v-model.number="age" class="form-control">
                </div>
            </form>
        </b-modal>
  </b-container>
</template>
<script>
import { Alert } from "bootstrap";
import books from "../services/Books";

export default {
  data() {
    return {
      author: "",
      nameBook: "",
      gener: "",
      age: "",
      books: [],
      status: false,
      cargando: false,
      updateBook: {
        id: null,
        author: "",
        nameBook: "",
        gener: "",
        age: "",
      },
    };
  },
  mounted() {
    this.consultBooks();
  },
  methods: {
    async insertBook(event) {
      const ageNew = new Date().getFullYear();
      try {
        if (this.age > ageNew) {
          return alert("El año de edicion no es el correcto");
        }
        if (this.status) {
          await books.onUpdate(
            this.updateBook.id,
            this.author,
            this.nameBook,
            this.gener,
            this.age
          );
        } else {
          if (this.age > ageNew) {
            return alert("El año de edicion no es el correcto");
          }
          await books.onInsert(
            this.author,
            this.nameBook,
            this.gener,
            this.age
          );
        }
        this.consultBooks();
      } catch (error) {
        throw error;
      } finally {
        this.$bvModal.hide("modal-1");
        this.author = "";
        this.nameBook = "";
        this.gener = "";
        this.age = "";
      }
    },

    async consultBooks() {
      try {
        this.cargando = true;
        const resutl = await books.onGetAll();
        this.books = resutl;
      } catch (error) {
        throw error;
      } finally {
        this.cargando = false;
      }
    },

    async deleteBooks(libroId) {
      const result = await books.onDelete(libroId);
      console.log(result);
      this.consultBooks();
    },

    updateBooks(libro) {
      this.updateBook = { ...libro };
      this.author = this.updateBook.author;
      this.nameBook = this.updateBook.nameBook;
      this.gener = this.updateBook.gener;
      this.age = this.updateBook.age;
      this.$bvModal.show("modal-1");
      this.status = true;
    },
  },
};
</script>
<style lang=""></style>
