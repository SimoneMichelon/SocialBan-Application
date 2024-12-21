import { Component, OnInit } from "@angular/core";
import { AuthService } from "src/app/services/Auth/auth.service";
import { PostService } from "src/app/services/Post/post.service";

@Component({
  selector: "app-form-post",
  templateUrl: "./form-post.component.html",
  styleUrls: ["./form-post.component.css"],
})
export class FormPostsComponent implements OnInit {
  userAuth: any;

  postItem: any = {
    title: "",
    image: "",
    description: "",
  };

  constructor(
    private authService: AuthService,
    private postService: PostService // public dialogRef: MatDialogRef<HomePageComponent>
  ) {}

  url = "";

  ngOnInit(): void {
    this.authService.getUserProfile().subscribe({
      next: (data) => (this.userAuth = data),
      error: (error) => console.log("error", error),
    });
  }

  onSubmit() {
    this.postService.createPost(this.postItem).subscribe({
      next: (data) => console.log("created post", data),
      error: (error) => console.log("error", error),
    });
  }

  isEmpty(): boolean{
    return  this.postItem.title.length < 1 ? true  : false;
  }

}
