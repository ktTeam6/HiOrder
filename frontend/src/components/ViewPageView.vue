<template>

    <v-data-table
        :headers="headers"
        :items="viewPage"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'ViewPageView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "id", value: "id" },
                { text: "name", value: "name" },
                { text: "description", value: "description" },
                { text: "price", value: "price" },
                { text: "roomStatus", value: "roomStatus" },
                { text: "reservationStatus", value: "reservationStatus" },
            ],
            viewPage : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/viewPages'))

            temp.data._embedded.viewPages.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.viewPage = temp.data._embedded.viewPages;
        },
        methods: {
        }
    }
</script>

