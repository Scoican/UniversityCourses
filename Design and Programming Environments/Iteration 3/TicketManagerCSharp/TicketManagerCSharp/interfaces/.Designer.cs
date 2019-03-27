namespace TicketManagerCSharp
{
    partial class GUIcs
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.eventsDataGrid = new System.Windows.Forms.DataGridView();
            this.sellButton = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.eventsDataGrid)).BeginInit();
            this.SuspendLayout();
            // 
            // eventsDataGrid
            // 
            this.eventsDataGrid.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.eventsDataGrid.Location = new System.Drawing.Point(13, 13);
            this.eventsDataGrid.Margin = new System.Windows.Forms.Padding(4);
            this.eventsDataGrid.Name = "eventsDataGrid";
            this.eventsDataGrid.Size = new System.Drawing.Size(691, 350);
            this.eventsDataGrid.TabIndex = 0;
            // 
            // sellButton
            // 
            this.sellButton.Location = new System.Drawing.Point(231, 371);
            this.sellButton.Margin = new System.Windows.Forms.Padding(4);
            this.sellButton.Name = "sellButton";
            this.sellButton.Size = new System.Drawing.Size(252, 59);
            this.sellButton.TabIndex = 1;
            this.sellButton.Text = "Sell Ticket";
            this.sellButton.UseVisualStyleBackColor = true;
            this.sellButton.Click += new System.EventHandler(this.sellButton_Click);
            // 
            // GUIcs
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(717, 442);
            this.Controls.Add(this.sellButton);
            this.Controls.Add(this.eventsDataGrid);
            this.Margin = new System.Windows.Forms.Padding(4);
            this.Name = "GUIcs";
            this.Text = "GUIcs";
            ((System.ComponentModel.ISupportInitialize)(this.eventsDataGrid)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.DataGridView eventsDataGrid;
        private System.Windows.Forms.Button sellButton;
    }
}