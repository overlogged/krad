## git notes
    git branch 查看分支
    git checkout xxxx 切换到分支
    git checkout -b your_name 创建个人本地分支

    日常工作流程
    git checkout dev
    git pull origin dev
    git checkout your_name
    git merge dev
    # working
    git add .
    git commit -m "add/fix/modify/remove"
    git checkout dev
    git pull origin dev
    
    # 以上操作均不会出错
    git merge your_name
    有可能有冲突
    解决冲突，并commit

    # 以下操作不会出错
    git push
    
## idea notes
    import project，选择pom.xml