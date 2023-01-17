new Vue({
    el: '#app',
    data: {
        posts: [],
        newPost: { title:"", url:""}
    },
    created: function () {
        this.loadBookmarks();
    },
    methods: {
        loadBookmarks() {
            let self = this;
            $.getJSON("/api/posts", function (data) {
                self.posts = data
            });
        },
        saveBookmark() {
            let self = this;
            if(this.newPost.title.trim() === "" || this.newPost.url.trim() === "") {
                alert("Please enter title and url");
                return;
            }
            $.ajax({
                type: "POST",
                url: '/api/posts',
                data: JSON.stringify(this.newPost),
                contentType: "application/json",
                success: function () {
                    self.newPost = {};
                    self.loadBookmarks();
                }
            });
        },
        addVote(postId, value) {
            let self = this;
            $.ajax({
                type: "POST",
                url: '/api/posts/'+postId+'/votes',
                data: JSON.stringify({"value": value}),
                contentType: "application/json",
                success: function () {
                    self.loadBookmarks();
                }
            });
        },
    }
});