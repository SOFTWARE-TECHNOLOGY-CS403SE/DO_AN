# DO_AN


## HƯỚNG DẪN SỬ DỤNG GIT

```bash

- Xem tất cả các nhánh từ xa
$ git branch -r

- Xem tất cả các nhánh cục bộ và từ xa
$ git branch -a

- Xem tất cả các nhánh cục bộ
$ git branch

- Buộc xóa nhánh
$ git branch -D <branch name>

- Xóa nhánh từ xa
$ git push origin --delete <branch name>

- Tạo nhánh mới
$ git checkout -b <branch name>

- Chuyển sang nhánh khác
$ git checkout <branch name>

- Xem các thay đổi vừa sửa trong các file
$ git checkout

- Undo lại commit đã đẩy lên
$ git revert HEAD

- Undo lại commit đã đẩy lên theo id
$ git revert 697f8fa

- Undo lại commit vừa commit
$ git reset --soft HEAD~1

- Xóa commit đã đẩy lên
$ git reset --hard ae8e8637^
$ git push origin HEAD --force

- Kiểm tra các commit 
$ git reflog

- Undo lại commit vừa bị xóa
$ git reset --hard ae8e8637
$ git push origin HEAD --force
```