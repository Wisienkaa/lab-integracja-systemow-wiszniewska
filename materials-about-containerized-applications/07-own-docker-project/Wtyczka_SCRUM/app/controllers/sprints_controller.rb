class SprintsController < ApplicationController
    before_action :set_sprint, only: [:show, :edit, :update, :destroy]
  
    # GET /sprints
    def index
      @sprints = Sprint.all
    end
  
    # GET /sprints/new
    def new
      @sprint = Sprint.new
    end
  
    # POST /sprints
    def create
        @sprint = Sprint.new(sprint_params)
      
        # Ustawiamy przypisanie projektów, jeżeli zostały wybrane w formularzu
        if params[:sprint][:project_ids]
          @sprint.project_ids = params[:sprint][:project_ids]
        end
      
        if @sprint.save
          redirect_to sprints_path, notice: 'Sprint został utworzony.'
        else
          render :new
        end
      end
      
  
    # GET /sprints/:id
    def show
    end
  
    # GET /sprints/:id/edit
    def edit
    end
  
    # PATCH/PUT /sprints/:id
    def update
      if @sprint.update(sprint_params)
        redirect_to sprints_path, notice: 'Sprint został zaktualizowany.'
      else
        render :edit
      end
    end
  
    # DELETE /sprints/:id
    def destroy
      @sprint.destroy
      redirect_to sprints_path, notice: 'Sprint został usunięty.'
    end
  
    private
  
    def set_sprint
      @sprint = Sprint.find(params[:id])
    end
  
    def sprint_params
        params.require(:sprint).permit(:name, :start_date, :due_date, project_ids: [])
    end      
  end
  